CREATE SCHEMA IF NOT EXISTS pizza;

-- таблица ингредиентов
CREATE TABLE pizza.t_ingredients
(
    id    SERIAL PRIMARY KEY,
    name  VARCHAR(100)   NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    CONSTRAINT uk_ingredient_name UNIQUE (name)
);

-- таблица основ для пиццы
CREATE TABLE pizza.t_pizza_bases
(
    id    SERIAL PRIMARY KEY,
    type  VARCHAR(50)    NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    CONSTRAINT uk_pizza_base_type UNIQUE (type)
);

-- таблица бортов
CREATE TABLE pizza.t_pizza_boards
(
    id SERIAL PRIMARY KEY,
    price DECIMAL(10, 2) NOT NULL
);

-- таблица пицц
CREATE TABLE pizza.t_pizzas
(
    id             SERIAL PRIMARY KEY,
    name           VARCHAR(100)   NOT NULL,
    pizza_base_id  INTEGER        NOT NULL,
    price          DECIMAL(10, 2) NOT NULL,
    CONSTRAINT fk_pizza_base FOREIGN KEY (pizza_base_id) REFERENCES pizza.t_pizza_bases (id)
);

-- связующая таблица для ингредиентов пиццы(многие-ко-многим)
CREATE TABLE pizza.t_pizza_ingredients
(
    pizza_id      INTEGER NOT NULL,
    ingredient_id INTEGER NOT NULL,
    PRIMARY KEY (pizza_id, ingredient_id),
    CONSTRAINT fk_pizza_ingredient_pizza FOREIGN KEY (pizza_id) REFERENCES pizza.t_pizzas (id) ON DELETE CASCADE,
    CONSTRAINT fk_pizza_ingredient_ingredient FOREIGN KEY (ingredient_id) REFERENCES pizza.t_ingredients (id) ON DELETE CASCADE
);

-- связующая таблица для ингредиентов борта(многие-ко-многим)
CREATE TABLE pizza.t_pizza_board_ingredients
(
    pizza_board_id INTEGER NOT NULL,
    ingredient_id  INTEGER NOT NULL,
    PRIMARY KEY (pizza_board_id, ingredient_id),
    CONSTRAINT fk_board_ingredient_board FOREIGN KEY (pizza_board_id) REFERENCES pizza.t_pizza_boards (id) ON DELETE CASCADE,
    CONSTRAINT fk_board_ingredient_ingredient FOREIGN KEY (ingredient_id) REFERENCES pizza.t_ingredients (id) ON DELETE CASCADE
);

-- связующая таблица для доступных пицц для бортов(многие-ко-многим)
CREATE TABLE pizza.t_pizza_board_available_pizzas
(
    pizza_board_id INTEGER NOT NULL,
    pizza_id       INTEGER NOT NULL,
    PRIMARY KEY (pizza_board_id, pizza_id),
    CONSTRAINT fk_board_pizza_board FOREIGN KEY (pizza_board_id) REFERENCES pizza.t_pizza_boards (id) ON DELETE CASCADE,
    CONSTRAINT fk_board_pizza_pizza FOREIGN KEY (pizza_id) REFERENCES pizza.t_pizzas (id) ON DELETE CASCADE
);

-- индексы
CREATE INDEX idx_ingredients_name ON pizza.t_ingredients (name);
CREATE INDEX idx_pizza_bases_type ON pizza.t_pizza_bases (type);
CREATE INDEX idx_pizzas_name ON pizza.t_pizzas (name);
CREATE INDEX idx_pizzas_base ON pizza.t_pizzas (pizza_base_id);
CREATE INDEX idx_pizza_ingredients_pizza ON pizza.t_pizza_ingredients (pizza_id);
CREATE INDEX idx_pizza_ingredients_ingredient ON pizza.t_pizza_ingredients (ingredient_id);
CREATE INDEX idx_board_ingredients_board ON pizza.t_pizza_board_ingredients (pizza_board_id);
CREATE INDEX idx_board_ingredients_ingredient ON pizza.t_pizza_board_ingredients (ingredient_id);
CREATE INDEX idx_board_available_pizzas_board ON pizza.t_pizza_board_available_pizzas (pizza_board_id);
CREATE INDEX idx_board_available_pizzas_pizza ON pizza.t_pizza_board_available_pizzas (pizza_id);