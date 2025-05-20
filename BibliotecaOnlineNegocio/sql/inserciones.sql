

USE biblioteca_online;

-- insercion de autores

INSERT INTO autor (nombre, biografia) VALUES
 ('F. Scott Fitzgerald', 'Autor estadounidense, conocido por "The Great Gatsby".'),
 ('Herman Melville', 'Escritor estadounidense, autor de "Moby-Dick".'),
 ('Leo Tolstoy', 'Escritor ruso, autor de "War and Peace".'),
 ('Fyodor Dostoevsky', 'Escritor ruso, autor de "Crime and Punishment".'),
 ('Jane Austen', 'Novelista inglesa, autora de "Pride and Prejudice".'),
 ('Harper Lee', 'Autora estadounidense, conocida por "To Kill a Mockingbird".'),
 ('George Orwell', 'Autor inglés, conocido por "1984".'),
 ('Dan Brown', 'Autor estadounidense, famoso por "The Da Vinci Code".'),
 ('Mary Shelley', 'Escritora inglesa, autora de "Frankenstein".'),
 ('J.R.R. Tolkien', 'Filólogo y escritor británico, autor de "The Hobbit".'),
 ('Bram Stoker', 'Escritor irlandés, autor de "Dracula".'),
 ('J.D. Salinger', 'Escritor estadounidense, autor de "The Catcher in the Rye".'),
 ('Lewis Carroll', 'Autor inglés, conocido por "Alice in Wonderland".'),
 ('Suzanne Collins', 'Autora estadounidense, creadora de "The Hunger Games".'),
 ('Ray Bradbury', 'Escritor estadounidense, autor de "Fahrenheit 451".'),
 ('Aldous Huxley', 'Filósofo y escritor inglés, autor de "Brave New World".'),
 ('C.S. Lewis', 'Ensayista y novelista británico, autor de "The Lion, the Witch and the Wardrobe".'),
 ('Victor Hugo', 'Escritor francés, autor de "Les Miserables".'),
 ('Miguel de Cervantes', 'Escritor español, autor de "Don Quixote".'),
 ('Homer', 'Poeta griego de la antigüedad, autor de "The Iliad".'),
 ('Dante Alighieri', 'Poeta italiano, autor de "The Divine Comedy".'),
 ('James Joyce', 'Escritor irlandés, autor de "Ulysses".'),
 ('Charlotte Brontë', 'Novelista inglesa, autora de "Jane Eyre".'),
 ('Emily Brontë', 'Poeta y novelista inglesa, autora de "Wuthering Heights".'),
 ('Alexandre Dumas', 'Escritor francés, autor de "The Count of Monte Cristo".'),
 ('Gustave Flaubert', 'Escritor francés, autor de "Madame Bovary".'),
 ('Paulo Coelho', 'Autor brasileño, conocido por "The Alchemist".'),
 ('Stephen King', 'Escritor estadounidense, autor de "The Shining".'),
 ('Agatha Christie', 'Novelista británica, autora de "Murder on the Orient Express".'),
 ('Mark Twain', 'Escritor estadounidense, autor de "Adventures of Huckleberry Finn".');

INSERT INTO autor (nombre, biografia) VALUES
('Gabriel Garcia Marquez', 'Escritor colombiano, ganador del Premio Nobel, autor de "Cien anos de soledad".'),
('J.K. Rowling', 'Autora britanica famosa por la serie de libros de Harry Potter.'),
('George Orwell', 'Autor ingles conocido por "1984" y "Rebelion en la granja".'),
('Jane Austen', 'Novelista inglesa del siglo XIX, autora de "Orgullo y prejuicio".'),
('Leo Tolstoy', 'Escritor ruso de "Guerra y paz" y "Anna Karenina".'),
('Mark Twain', 'Autor estadounidense de "Las aventuras de Tom Sawyer" y "Huckleberry Finn".'),
('Ernest Hemingway', 'Escritor y periodista estadounidense, Nobel de Literatura, autor de "El viejo y el mar".'),
('Franz Kafka', 'Autor checo, reconocido por obras como "La metamorfosis" y "El proceso".'),
('Fyodor Dostoevsky', 'Escritor ruso, autor de "Crimen y castigo" y "Los hermanos Karamazov".'),
('Haruki Murakami', 'Autor japones contemporaneo conocido por "Tokio Blues" y "Kafka en la orilla".'),
('Isabel Allende', 'Escritora chilena, conocida por "La casa de los espiritus".'),
('Stephen King', 'Autor estadounidense de terror y suspenso, conocido por "It", "El resplandor".'),
('J.R.R. Tolkien', 'Filologo y escritor britanico, creador de "El senor de los anillos".'),
('Agatha Christie', 'Autora britanica de misterio, famosa por "Diez negritos" y "Asesinato en el Orient Express".'),
('Miguel de Cervantes', 'Escritor espanol, autor de "Don Quijote de la Mancha".'),
('Charles Dickens', 'Autor ingles del siglo XIX, conocido por "Oliver Twist" y "Un cuento de Navidad".'),
('Paulo Coelho', 'Autor brasileno, famoso por "El alquimista".'),
('Victor Hugo', 'Escritor frances, autor de "Los miserables" y "Nuestra Senora de Paris".'),
('Mary Shelley', 'Autora inglesa de "Frankenstein", considerada pionera de la ciencia ficcion.'),
('Homer', 'Poeta griego de la antiguedad, autor de "La Iliada" y "La Odisea".');

-- insercion de generos
INSERT INTO genero (nombre) VALUES
('Fantasia'),
('Ciencia Ficcion'),
('Misterio'),
('Terror'),
('Romance'),
('Aventura'),
('Drama'),
('Historico'),
('Biografia'),
('Autobiografia'),
('Poesia'),
('Thriller'),
('Literatura Clasica'),
('Distopia'),
('Comedia'),
('Literatura Infantil'),
('Young Adult'),
('No Ficcion'),
('Mitologia'),
('Crimen'),
('Manga');

-- insercion de libros
INSERT INTO libro (anio_publicacion, contenido_adulto, descripcion, editorial, isbn, nombre, num_paginas, autor_id) VALUES
(1954, 0, 'Primera parte de la epica trilogia de fantasia.', 'George Allen & Unwin', '9780261103573', 'La Comunidad del Anillo', 423, 1),
(1997, 0, 'Primera entrega de la saga de Harry Potter.', 'Bloomsbury', '9780747532743', 'Harry Potter y la piedra filosofal', 223, 2),
(1949, 0, 'Distopia clasica sobre un regimen totalitario.', 'Secker & Warburg', '9780451524935', '1984', 328, 3),
(2003, 0, 'Un misterio religioso con elementos historicos.', 'Doubleday', '9780385504201', 'El codigo Da Vinci', 454, 4),
(1960, 0, 'Una historia sobre racismo e injusticia en el sur de EE.UU.', 'J.B. Lippincott & Co.', '9780061120084', 'Matar a un ruisenor', 281, 5),
(1813, 0, 'Una obra maestra de la literatura romantica.', 'T. Egerton', '9781503290563', 'Orgullo y prejuicio', 279, 6),
(2005, 0, 'Novela distopica juvenil sobre lucha y supervivencia.', 'Scholastic Press', '9780439023481', 'Los juegos del hambre', 374, 7),
(1897, 0, 'Un clasico del terror gotico con vampiros.', 'Archibald Constable and Company', '9780141439846', 'Dracula', 418, 8),
(1951, 0, 'Historia de un joven alienado en Nueva York.', 'Little, Brown and Company', '9780316769488', 'El guardian entre el centeno', 277, 9),
(1865, 0, 'Aventura fantastica en un mundo de maravillas.', 'Macmillan', '9780486275437', 'Alicia en el pais de las maravillas', 201, 10);
