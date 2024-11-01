-- Insert dummy data into the 'books' table
INSERT INTO books (id, title, author, release_year, genre, summary, cover_url) VALUES
('1', 'The Great Gatsby', 'F. Scott Fitzgerald', 1925, 'Fiction', 'A story about the mysterious Jay Gatsby and his love for Daisy Buchanan.', 'https://example.com/gatsby.jpg'),
('2', '1984', 'George Orwell', 1949, 'Dystopian', 'A chilling depiction of a totalitarian regime and the effects of surveillance.', 'https://example.com/1984.jpg'),
('3', 'To Kill a Mockingbird', 'Harper Lee', 1960, 'Fiction', 'A novel about racial injustice in the Deep South, seen through the eyes of young Scout Finch.', 'https://example.com/mockingbird.jpg');

-- Insert dummy data into the 'users' table
INSERT INTO users (first_name, last_name, username, password, avatar_url) VALUES
('Alice', 'Johnson', 'alicej', 'password123', 'https://example.com/alice.jpg'),
('Bob', 'Smith', 'bobsmith', 'securepass456', 'https://example.com/bob.jpg'),
('Charlie', 'Brown', 'charlieb', 'mypassword789', 'https://example.com/charlie.jpg');

-- Insert dummy data into the 'reading_lists' table
INSERT INTO reading_lists (user_id, book_id, is_read, tag) VALUES
(1, '1', 1, 'Classic'),
(2, '2', 0, 'Dystopian'),
(3, '3', 1, 'Must-Read');
