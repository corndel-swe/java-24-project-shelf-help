DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS reading_lists;

-- Create the 'books' table
CREATE TABLE IF NOT EXISTS books (
    id TEXT PRIMARY KEY NOT NULL,
    title TEXT NOT NULL,
    author TEXT NOT NULL,
    release_year INTEGER NOT NULL,
    average_rating FLOAT NOT NULL,
    summary TEXT NOT NULL,
    cover_url TEXT NOT NULL
);

-- Create the 'users' table
CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    username TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    avatar_url TEXT NOT NULL
);

-- Create the 'reading_lists' table
CREATE TABLE IF NOT EXISTS reading_lists (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER REFERENCES users(id),
    book_id INTEGER REFERENCES books(id),
    is_read INTEGER NOT NULL DEFAULT 0,
    tag TEXT
);

-- Create the 'reviews' table with a timestamp
CREATE TABLE IF NOT EXISTS reviews (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER REFERENCES users(id),
    book_id INTEGER REFERENCES books(id),
    content TEXT NOT NULL,
    rating INTEGER NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);