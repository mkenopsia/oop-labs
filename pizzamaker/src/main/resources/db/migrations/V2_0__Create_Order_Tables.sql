CREATE SCHEMA IF NOT EXISTS ordering;

CREATE TABLE ordering.t_pizza_order
(
    id      uuid primary key,
    user_id int            not null,
    date    timestamp without time zone,
    status  varchar(50)    not null,
    comment varchar(500),
    price   decimal(10, 2) not null
);

CREATE INDEX idx_order_user ON ordering.t_pizza_order (user_id);
CREATE INDEX idx_order_status ON ordering.t_pizza_order (status);

CREATE TABLE ordering.t_pizza_for_order
(
    id       serial primary key,
    order_id uuid          not null,
    name     varchar(100) not null,
    size     varchar(10)  not null,
    board_id int,
    constraint fk_pizza_order foreign key (order_id) references ordering.t_pizza_order (id) on delete cascade,
    constraint fk_pizza_board foreign key (board_id) references pizza.t_pizza_boards (id) on delete cascade
);

CREATE INDEX idx_pizza_for_order_order ON ordering.t_pizza_for_order (order_id);
CREATE INDEX idx_pizza_for_order_board ON ordering.t_pizza_for_order (board_id);

CREATE TABLE ordering.t_ingredients_for_pizza_for_order
(
    pizza_id      int not null,
    ingredient_id int not null,
    constraint fk_pizza_for_order foreign key (pizza_id) references ordering.t_pizza_for_order (id) on delete cascade,
    constraint fk_ingredient foreign key (ingredient_id) references pizza.t_ingredients (id) on delete cascade
);

CREATE INDEX idx_ingredients_for_pizza ON ordering.t_ingredients_for_pizza_for_order (pizza_id);
CREATE INDEX idx_ingredients_ingredient ON ordering.t_ingredients_for_pizza_for_order (ingredient_id);