document.addEventListener("DOMContentLoaded", renderCart);
document.getElementById("make-order").addEventListener("click", showModalWindow)
document.getElementById("submit-order").addEventListener("click", makeOrder) 
let modalWindow = document.getElementById("modal-window");

class PizzaForOrder {
    constructor(name, size, pizzaBoardId, ingredients) {
        this.name = name;
        this.size = size;
        this.pizzaBoardId = pizzaBoardId;
        this.ingredients = ingredients;
    }
}

class Order {
    constructor(date, status, comment, price, pizzasForOrder) {
        this.userId = 123;
        this.date = date;
        this.status = status;
        this.comment = comment;
        this.price = price;
        this.pizzasForOrder = pizzasForOrder;
    }
}

let ORDERSUM = 0;

function renderCart() {
    const container = document.getElementById("pizzas-container");
    container.innerHTML = '';

    const cart = getCart();

    console.log(cart);

    for(let pizza of cart) {
        let card = document.createElement('div'); 
        let pizzaPrice = ((pizza.price + pizza.pizzaBoard.price) * getCoef(pizza));
        ORDERSUM += pizzaPrice;
        card.innerHTML = `
            <div class="pizza-card" data-id="${pizza.id}">
                <h3>${pizza.name}</h3>
                <div class="price">${pizzaPrice.toFixed(2)} ₽</div>
                <div class="base">Основа: ${pizza.pizzaBase.type}</div>
                <div class="ingredients">
                <h4>Ингредиенты:</h4>
                <ul>
                    ${pizza.ingredientNames.map(ing => 
                    `<li>${ing}</li>`
                    ).join('')}
                </ul>
                </div>
                <h4>Размер: ${pizza.size}</h4>
                <h4>Бортик: ${pizza.pizzaBoard.name}</h4>
                <button class="delete" data-id="${pizza.id}">Удалить</button>
            </div>
            `;
        container.appendChild(card);
    }

    let priceContainer =  document.getElementById("price");
    priceContainer.innerHTML = `Сумма заказа: ${ORDERSUM.toFixed(2)}`;

    document.querySelectorAll('.delete').forEach(button => {
        button.addEventListener('click', (e) => {
            const pizzaId = parseInt(e.target.getAttribute('data-id'));
            deletePizzaFromCart(pizzaId);
        });
    });
}

function getCoef(pizza) {
    if(pizza.size === "25см") {
        return 0.9;
    } else if(pizza.size === "30см") {
        return 1;
    } else {
        return 1.2;
    }
}

function getCart() {
    const cartString = localStorage.getItem("cart");
    const cart = cartString ? JSON.parse(cartString) : [];
    return cart;
}

function deletePizzaFromCart(id) {
    let cart = getCart();
    const index = cart.findIndex(pzz => pzz.id === id);
    cart.splice(index, 1);
    localStorage.setItem("cart", JSON.stringify(cart));
    ORDERSUM = 0;
    renderCart();
}

function showModalWindow() {
    modalWindow.style.display = 'flex'
}

function getDateTime() {
    const dateInput = document.getElementById("date").value;
    const timeInput = document.getElementById("time").value;

    if (!dateInput && !timeInput) {
        return null;
    }

    if (!dateInput || !timeInput) {
        alert("Выберите и дату, и время");
        return;
    }

    return `${dateInput}T${timeInput}`;
    // console.log(dateTimeString);
}

function makeOrder() {
    let date = getDateTime();
    let status = (date === null) ? "NEW" :"SCHEDULED";
    let comment = document.getElementById("comment").value;
    let price = ORDERSUM.toFixed(2);
    let pizzasForOrder = [];
    for(let pizzaInCart of getCart()) {
        pizzasForOrder.push(
            new PizzaForOrder(pizzaInCart.name, pizzaInCart.size, pizzaInCart.pizzaBoardId, pizzaInCart.ingredientNames));
    }
    let order = new Order(date, status, comment, price, pizzasForOrder);
    let orderJsonRequest = JSON.stringify(order);
    console.log(order);
    let response = fetchOrder(orderJsonRequest);
    modalWindow.style.display = 'none';

    localStorage.clear();
    ORDERSUM = 0;

    renderCart();
}

function fetchOrder(orderJson) {
    fetch("http://localhost:8080/pizzamaker/make-order", 
        {
            method:'POST',
            headers: {
                "Content-Type": "application/json"
            },
            body: orderJson
        }
    )
    .then(response => {
        if(!response.ok) {
            throw new Error("Ошибочка");
        }
        alert("Заказ успешно оформлен 👍");
    })
    .catch(error => {
        console.error("Ошибка отправки заказа:", error);
        alert("Ошибка при отправке заказа.");
      });
}