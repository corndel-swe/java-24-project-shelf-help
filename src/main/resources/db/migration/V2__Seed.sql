-- Insert dummy data into the 'books' table with average ratings and real cover images
INSERT INTO books (id, title, author, release_year, average_rating, summary, cover_url) VALUES
(1, 'The Great Gatsby', 'F. Scott Fitzgerald', 1925, 4.3, 'A story about the mysterious Jay Gatsby and his love for Daisy Buchanan.', 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/f7/The_Great_Gatsby_%281924%29.jpg/220px-The_Great_Gatsby_%281924%29.jpg'),
(2, '1984', 'George Orwell', 1949, 4.2, 'A chilling depiction of a totalitarian regime and the effects of surveillance.', 'https://upload.wikimedia.org/wikipedia/en/thumb/c/c3/1984first.jpg/220px-1984first.jpg'),
(3, 'To Kill a Mockingbird', 'Harper Lee', 1960, 4.8, 'A novel about racial injustice in the Deep South, seen through the eyes of young Scout Finch.', 'https://upload.wikimedia.org/wikipedia/en/thumb/7/79/To_Kill_a_Mockingbird.JPG/220px-To_Kill_a_Mockingbird.JPG');


-- Insert dummy data into the 'users' table
INSERT INTO users (first_name, last_name, username, password, avatar_url) VALUES
('Alice', 'Johnson', 'alicej', 'password123', 'https://example.com/alice.jpg'),
('Bob', 'Smith', 'bobsmith', 'securepass456', 'https://example.com/bob.jpg'),
('Hala', 'Hassan', 'Hala', 'pass123', 'https://example.com/charlie.jpg');

-- Insert dummy data into the 'reading_lists' table
INSERT INTO reading_lists (user_id, book_id, is_read, tag) VALUES
(1, 1, 1, 'Classic'),
(2, 2, 0, 'Dystopian'),
(3, 3, 1, 'Must-Read');

-- Insert dummy data into the 'reviews' table
INSERT INTO reviews (user_id, book_id, content, rating) VALUES
(1, 1, 'An incredible story with fascinating characters.', 5),
(2, 2, 'A thought-provoking and chilling depiction of a dystopian future.', 4),
(3, 3, 'A beautiful story about justice and morality.', 5);