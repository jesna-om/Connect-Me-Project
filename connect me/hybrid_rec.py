import pandas as pd
import mysql.connector
from textblob import TextBlob  # For sentiment jyt5
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import numpy as np

# Sentiment Analysis
def calculate_sentiment(review_text):
    analysis = TextBlob(str(review_text))
    return analysis.sentiment.polarity  # Sentiment score between -1 (negative) and +1 (positive)

# Content-Based Filtering
def content_based_recommendation(user_id, df):
    user_reviews = df[df['user_id'] == user_id]
    other_reviews = df[df['user_id'] != user_id]
    
    # TF-IDF Vectorization
    tfidf = TfidfVectorizer(stop_words='english')
    tfidf_matrix = tfidf.fit_transform(df['review'].astype(str))
    
    if user_reviews.empty:
        print(f"No reviews found for user {user_id}")
        return []
    
    user_idx = user_reviews.index[0]  # Take the first review of the current user
    cosine_sim = cosine_similarity(tfidf_matrix)
    sim_scores = list(enumerate(cosine_sim[user_idx]))
    sim_scores = sorted(sim_scores, key=lambda x: x[1], reverse=True)
    
    top_matches = [df.iloc[i[0]]['worker_id'] for i in sim_scores[1:6]]
    return top_matches

# Collaborative Filtering
def collaborative_filtering_recommendation(user_id, df):
    # Create a user-item matrix
    user_item_matrix = df.pivot_table(
        index='user_id', columns='worker_id', values='sentiment_score', aggfunc='mean'
    ).fillna(0)
    
    # Check if user_id is in the index of user_item_matrix
    if user_id not in user_item_matrix.index:
        print(f"Error: user_id {user_id} not found in user-item matrix.")
        return []  # Return an empty list if user_id is not found
    
    # Compute cosine similarity between users
    user_similarity = cosine_similarity(user_item_matrix)
    
    # Ensure user_id is an integer or correctly typed for comparison
    try:
        user_index = user_item_matrix.index.tolist().index(int(user_id))  # Convert user_id to int
    except ValueError:
        print(f"Error: user_id {user_id} cannot be found or is of an incorrect type.")
        return []  # Return an empty list if there's a type mismatch or the user is not found
    
    similar_users = user_similarity[user_index]
    similar_users_indices = np.argsort(similar_users)[::-1][1:]  # Skip the target user
    
    # Aggregate scores from similar users
    worker_scores = {}
    for idx in similar_users_indices:
        similar_user_id = user_item_matrix.index[idx]
        similar_user_ratings = user_item_matrix.loc[similar_user_id]
        
        for worker_id, score in similar_user_ratings.items():
            if worker_id not in worker_scores:
                worker_scores[worker_id] = 0
            worker_scores[worker_id] += similar_users[idx] * score
    
    # Sort by score and return top worker IDs
    recommended_workers = sorted(worker_scores, key=worker_scores.get, reverse=True)
    return recommended_workers[:5]

# Hybrid Recommendation
def hybrid_recommendation(user_id, df):
    df['sentiment_score'] = df['review'].astype(str).apply(calculate_sentiment)
    
    content_rec = content_based_recommendation(user_id, df)
    collab_rec = collaborative_filtering_recommendation(user_id, df)
    
    # Combine recommendations (union of both methods)
    combined_rec = list(set(content_rec).union(set(collab_rec)))
    
    # Rank combined recommendations by average sentiment score
    worker_sentiment_scores = df.groupby('worker_id')['sentiment_score'].mean().to_dict()
    ranked_rec = sorted(combined_rec, key=lambda worker_id: worker_sentiment_scores.get(worker_id, 0), reverse=True)
    ranked_rec = [int(worker_id) for worker_id in ranked_rec]
    
    return ranked_rec