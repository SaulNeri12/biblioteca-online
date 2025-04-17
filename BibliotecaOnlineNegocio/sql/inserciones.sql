

USE biblioteca_online;

-- insercion de autores
INSERT INTO autor (nombre, biografia) VALUES
('Gabriel García Márquez', 'Escritor colombiano, ganador del Premio Nobel, autor de "Cien años de soledad".'),
('J.K. Rowling', 'Autora británica famosa por la serie de libros de Harry Potter.'),
('George Orwell', 'Autor inglés conocido por "1984" y "Rebelión en la granja".'),
('Jane Austen', 'Novelista inglesa del siglo XIX, autora de "Orgullo y prejuicio".'),
('Leo Tolstoy', 'Escritor ruso de "Guerra y paz" y "Anna Karénina".'),
('Mark Twain', 'Autor estadounidense de "Las aventuras de Tom Sawyer" y "Huckleberry Finn".'),
('Ernest Hemingway', 'Escritor y periodista estadounidense, Nobel de Literatura, autor de "El viejo y el mar".'),
('Franz Kafka', 'Autor checo, reconocido por obras como "La metamorfosis" y "El proceso".'),
('Fyodor Dostoevsky', 'Escritor ruso, autor de "Crimen y castigo" y "Los hermanos Karamazov".'),
('Haruki Murakami', 'Autor japonés contemporáneo conocido por "Tokio Blues" y "Kafka en la orilla".'),
('Isabel Allende', 'Escritora chilena, conocida por "La casa de los espíritus".'),
('Stephen King', 'Autor estadounidense de terror y suspenso, conocido por "It", "El resplandor".'),
('J.R.R. Tolkien', 'Filólogo y escritor británico, creador de "El señor de los anillos".'),
('Agatha Christie', 'Autora británica de misterio, famosa por "Diez negritos" y "Asesinato en el Orient Express".'),
('Miguel de Cervantes', 'Escritor español, autor de "Don Quijote de la Mancha".'),
('Charles Dickens', 'Autor inglés del siglo XIX, conocido por "Oliver Twist" y "Un cuento de Navidad".'),
('Paulo Coelho', 'Autor brasileño, famoso por "El alquimista".'),
('Victor Hugo', 'Escritor francés, autor de "Los miserables" y "Nuestra Señora de París".'),
('Mary Shelley', 'Autora inglesa de "Frankenstein", considerada pionera de la ciencia ficción.'),
('Homer', 'Poeta griego de la antigüedad, autor de "La Ilíada" y "La Odisea".');

-- insercion de generos
INSERT INTO genero (nombre) VALUES
('Fantasía'),
('Ciencia Ficción'),
('Misterio'),
('Terror'),
('Romance'),
('Aventura'),
('Drama'),
('Histórico'),
('Biografía'),
('Autobiografía'),
('Poesía'),
('Thriller'),
('Literatura Clásica'),
('Distopía'),
('Comedia'),
('Literatura Infantil'),
('Young Adult'),
('No Ficción'),
('Mitología'),
('Crimen'),
('Manga');

INSERT INTO libro (anio_publicacion, contenido_adulto, descripcion, editorial, isbn, nombre, num_paginas, autor_id) VALUES
(1954, 0, 'Primera parte de la épica trilogía de fantasía.', 'George Allen & Unwin', '9780261103573', 'La Comunidad del Anillo', 423, 1),
(1997, 0, 'Primera entrega de la saga de Harry Potter.', 'Bloomsbury', '9780747532743', 'Harry Potter y la piedra filosofal', 223, 2),
(1949, 0, 'Distopía clásica sobre un régimen totalitario.', 'Secker & Warburg', '9780451524935', '1984', 328, 3),
(2003, 0, 'Un misterio religioso con elementos históricos.', 'Doubleday', '9780385504201', 'El código Da Vinci', 454, 4),
(1960, 0, 'Una historia sobre racismo e injusticia en el sur de EE.UU.', 'J.B. Lippincott & Co.', '9780061120084', 'Matar a un ruiseñor', 281, 5),
(1813, 0, 'Una obra maestra de la literatura romántica.', 'T. Egerton', '9781503290563', 'Orgullo y prejuicio', 279, 6),
(2005, 0, 'Novela distópica juvenil sobre lucha y supervivencia.', 'Scholastic Press', '9780439023481', 'Los juegos del hambre', 374, 7),
(1897, 0, 'Un clásico del terror gótico con vampiros.', 'Archibald Constable and Company', '9780141439846', 'Drácula', 418, 8),
(1951, 0, 'Historia de un joven alienado en Nueva York.', 'Little, Brown and Company', '9780316769488', 'El guardián entre el centeno', 277, 9),
(1865, 0, 'Aventura fantástica en un mundo de maravillas.', 'Macmillan', '9780486275437', 'Alicia en el país de las maravillas', 201, 10);


