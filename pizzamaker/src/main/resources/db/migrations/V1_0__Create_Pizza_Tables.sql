CREATE SCHEMA IF NOT EXISTS pizza;

CREATE TABLE pizza.t_ingredients
(
    id    serial primary key,
    name  varchar(100)   not null ,
    price decimal(10, 2) not null ,
    constraint uk_ingredient_name unique (name)
);

CREATE TABLE pizza.t_pizza_bases
(
    id    serial primary key,
    type  varchar(50)    not null ,
    price decimal(10, 2) not null ,
    constraint uk_pizza_base_type unique (type)
);

CREATE TABLE pizza.t_pizza_boards
(
    id serial primary key ,
    name varchar(50) not null,
    price decimal(10, 2) not null
);

CREATE TABLE pizza.t_pizzas
(
    id             serial primary key ,
    name           varchar(100)   not null ,
    pizza_base_id  int        not null ,
    price          decimal(10, 2) not null ,
    constraint fk_pizza_base foreign key (pizza_base_id) references pizza.t_pizza_bases (id)
);

-- связующая таблица для ингредиентов (многие-ко-многим)
CREATE TABLE pizza.t_pizza_ingredients
(
    pizza_id      int not null ,
    ingredient_id int not null ,
    primary key (pizza_id, ingredient_id),
    constraint fk_pizza_ingredient_pizza foreign key (pizza_id) references pizza.t_pizzas (id) on delete cascade ,
    constraint fk_pizza_ingredient_ingredient foreign key (ingredient_id) references pizza.t_ingredients (id) on delete cascade
);

-- связующая таблица для ингредиентов борта(многие-ко-многим)
CREATE TABLE pizza.t_pizza_board_ingredients
(
    pizza_board_id int not null ,
    ingredient_id  int not null ,
    primary key (pizza_board_id, ingredient_id),
    constraint fk_board_ingredient_board foreign key (pizza_board_id) references pizza.t_pizza_boards (id) on delete cascade ,
    constraint fk_board_ingredient_ingredient foreign key (ingredient_id) references pizza.t_ingredients (id) on delete cascade
);

-- связующая таблица для доступных пицц для бортов(многие-ко-многим)
CREATE TABLE pizza.t_pizza_board_available_pizzas
(
    pizza_board_id int not null ,
    pizza_id       int not null ,
    primary key (pizza_board_id, pizza_id),
    constraint fk_board_pizza_board foreign key (pizza_board_id) references pizza.t_pizza_boards (id) on delete cascade ,
    constraint fk_board_pizza_pizza foreign key (pizza_id) references pizza.t_pizzas (id) on delete cascade
);

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