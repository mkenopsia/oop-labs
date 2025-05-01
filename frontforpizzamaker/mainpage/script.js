class Ingredient {
    constructor(id, name, price) {
      this.id = id;
      this.name = name;
      this.price = price;
    }
  
    static fromJson(json) {
      return new Ingredient(json.id, json.name, json.price);
    }
}

class PizzaBoard {
    constructor(id, name, availablePizzaIds, price) {
        this.id = id;
        this.name = name;
        this.availablePizzaIds = availablePizzaIds;
        this.price = price;
    }
  
    static fromJson(json) {
        return new PizzaBoard(json.id, json.name, json.availablePizzaIds, json.price);
    }
}
  
class PizzaBase {
    constructor(id, type, price) {
        this.id = id;
        this.type = type;
        this.price = price;
    }
  
    static fromJson(json) {
        return new PizzaBase(json.id, json.type, json.price);
    }
}

class PizzaBaseApiService {
    constructor() {
        this.pizzaBases = [];
    }
  
    async fetchPizzaBases() {
        try {
            const response = await fetch('http://localhost:8080/pizzamaker/pizzabases');
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const pizzaBasesJson = await response.json();
        this.pizzaBases = pizzaBasesJson.map(PizzaBase.fromJson);
        return this.pizzaBases;
        
        } catch (error) {
            console.error('Ошибка при загрузке пицц:', error);
            throw error;
        }
    }
  
    getPizzaBaseById(id) {
        return this.pizzaBases.find(pizza => pizza.id === id);
    }

    getPizzaBaseByType(type) {
        return this.pizzaBases.find(pizza => pizza.type === type);
    }

    getAllPizzaBases() {
        return this.pizzaBases;
    }
}
  
class Pizza {
    constructor(id, name, ingredients, ingredientNames, pizzaBase, price) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.ingredientNames = ingredientNames;
        this.pizzaBase = pizzaBase;
        this.price = price;
        this.size = '';
        this.pizzaBoard = null;
    }
  
    static fromJson(json) {
        let ingrNames = []
        for(let ingr of json.ingredients.map(Ingredient.fromJson)) {
            ingrNames.push(ingr.name);
        }
        return new Pizza(
        json.id,
        json.name,
        json.ingredients.map(Ingredient.fromJson),
        ingrNames,
        PizzaBase.fromJson(json.pizzaBase),
        json.price
        );
    }
  
    getInfo() {
        return {
            id: this.id,
            name: this.name,
            ingredients: this.ingredients,
            pizzaBase: this.pizzaBase,
            price: this.price
        };
    }
}
  
class PizzaApiService {
    constructor() {
        this.pizzas = [];
    }
  
    async fetchPizzas() {
        try {
            const response = await fetch('http://localhost:8080/pizzamaker/pizzas');
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const pizzasJson = await response.json();
        this.pizzas = pizzasJson.map(Pizza.fromJson);
        return this.pizzas;
        
        } catch (error) {
            console.error('Ошибка при загрузке пицц:', error);
            throw error;
        }
    }

    async fetchFilteredPizzas(filter) {
        try {
            const response = await fetch('http://localhost:8080/pizzamaker/pizzas' + "/" + filter);
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const pizzasJson = await response.json();
        this.pizzas = pizzasJson.map(Pizza.fromJson);
        return this.pizzas;
        
        } catch (error) {
            console.error('Ошибка при загрузке пицц:', error);
            throw error;
        }
    }
  
    getPizzaById(id) {
        return this.pizzas.find(pizza => pizza.id === id);
    }

    getAllPizzas() {
        return this.pizzas;
    }
}

class IngredientApiService {
    constructor() {
        this.ingredients = [];
    }
  
    async fetchIngredients() {
        try {
            const response = await fetch('http://localhost:8080/pizzamaker/ingredients');
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const ingredientsJson = await response.json();
        this.ingredients = ingredientsJson.map(Ingredient.fromJson);
        return this.ingredients;
        
        } catch (error) {
            console.error('Ошибка при загрузке ингредиентов', error);
            throw error;
        }
    }
  
    getIngredientById(id) {
        return this.ingredients.find(ingr => ingr.id === id);
    }

    getIngredientByName(name) {
        return this.ingredients.find(ingr => ingr.name === name);
    }

    getAllIngredients() {
        return this.ingredients;
    }
}

class PizzaBoardApiService {
    constructor(baseUrl = 'http://localhost:8080/pizzamaker') {
        this.baseUrl = baseUrl;
        this.boards = [];
    }
  
    async fetchPizzaBoards() {
        try {
            const response = await fetch(`${this.baseUrl}/pizzaboards`);
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const boardsJson = await response.json();
        this.boards = boardsJson.map(PizzaBoard.fromJson);
        return this.boards;
        
        } catch (error) {
            console.error('Ошибка при загрузке основ пицц:', error);
            throw error;
        }
    }
  
    getPizzaBoardById(id) {
        return this.boards.find(board => board.id === id);
    }

    getPizzaBoardByName(name) {
        return this.boards.find(board => board.name === name);
    }

    getAllPizzaBoards() {
        return this.boards;
    }
}

const pizzaService = new PizzaApiService();
const pizzaBoardService = new PizzaBoardApiService();
const ingredientService = new IngredientApiService();
const pizzaBaseService = new PizzaBaseApiService();

document.addEventListener('DOMContentLoaded', async () => {
    try {
        const pizzas = await pizzaService.fetchPizzas();
        console.log('Загружены пиццы:', pizzas);

        const boards = await pizzaBoardService.fetchPizzaBoards();
        console.log('Загружены бортики:', boards);

        const ingredients = await ingredientService.fetchIngredients();
        console.log('Загружены ингредиенты: ', ingredients)

        const pizzaBases = await pizzaBaseService.fetchPizzaBases();
        console.log('Загружены основы пицц: ', pizzaBases)

        localStorage.setItem("pizzas", JSON.stringify(pizzas));
        localStorage.setItem("pizzaBoards", JSON.stringify(boards));
        localStorage.setItem("ingredients", JSON.stringify(ingredients));
        localStorage.setItem("pizzaBases", JSON.stringify(pizzaBases));

        renderPizzas(pizzas);

    } catch (error) {
        console.error('Не удалось загрузить меню:', error);
    }
});

document.getElementById("do-filter").addEventListener("click", render);

async function render() {
    console.log(1);
    let filter = document.getElementById("filter").value;
    if(filter === null || filter.trim() === "") {
        try {
            renderPizzas(await pizzaService.fetchPizzas());
        } catch (error) {
            console.error('Не удалось загрузить меню:', error);
        }
    } else {
        try {
            renderPizzas(await pizzaService.fetchFilteredPizzas(filter));
        } catch (error) {
            console.error('Не удалось загрузить меню:', error);
        }
    }
}

function renderPizzas(pizzas) {
    let container = document.getElementById('pizzas-container');
    container.innerHTML = '';
    if (!container) return;

    container.innerHTML = pizzas.map(pizza => `
    <div class="pizza-card" data-id="${pizza.id}">
        <h3>${pizza.name}</h3>
        <div class="price">${pizza.price.toFixed(2)} ₽</div>
        <div class="base">Основа: ${pizza.pizzaBase.type}</div>
        <div class="ingredients">
        <h4>Ингредиенты:</h4>
        <ul>
            ${pizza.ingredientNames.map(ing => 
            `<li>${ing}</li>`
            ).join('')}
        </ul>
        </div>
        <button class="add-to-cart" data-id="${pizza.id}">Добавить в корзину</button>
    </div>
    `).join('');

    document.querySelectorAll('.add-to-cart').forEach(button => {
        button.addEventListener('click', (e) => {
            const pizzaId = parseInt(e.target.getAttribute('data-id'));
            const pizza = pizzaService.getPizzaById(pizzaId);
            temp.push(pizza);
            showModalWindow(pizzaId);
        });
    });
}

let temp = [];

function getCart() {
    const cart = localStorage.getItem("cart");
    return (cart != null) ? JSON.parse(cart) : [];
}

let modalWindow = document.getElementById("modal-window");
let addPizzaButton = document.getElementById("add-pizza-to-cart").addEventListener("click", addToCart);

let doublePizzaIngredients = new Map();

function showModalWindow(pizzaId) { //<option value="Стандартный">Стандартный</option>
    let boardInput = document.getElementById("board-input")
    boardInput.innerHTML = '';
    let standartOption = document.createElement("option")
    standartOption.value = "Стандартный";
    standartOption.textContent = "Стандартный";
    boardInput.appendChild(standartOption);

    for(const board of JSON.parse(localStorage.getItem("pizzaBoards"))) {
        if(board.availablePizzaIds.includes(pizzaId)) {
            let option = document.createElement("option");
            option.value = board.name;
            option.textContent = board.name + " " + board.price + " руб";
            boardInput.appendChild(option);
        }
    }

    let ingredientList = document.getElementById("multiply-ingredient-list");
    ingredientList.innerHTML = '';

    let ingredients = pizzaService.getPizzaById(pizzaId).ingredientNames;

    for(let i = 0; i < ingredients.length; i++) {
        let ingr = ingredients[i];
        const li = document.createElement("li");

        let count = 1;
        doublePizzaIngredients.set(ingr, count)
        let nameSpan = document.createElement("span");
        nameSpan.textContent = ingr;

        let controls = document.createElement("div");
        controls.className = "counter-controls";

        let countSpan = document.createElement("span");
        countSpan.className = "counter";
        countSpan.textContent = count;

        let minusBtn = document.createElement("button");
        minusBtn.textContent = "-";
        minusBtn.addEventListener("click", () => {
            if (count > 1) {
                count--;
                countSpan.textContent = count;
                doublePizzaIngredients.set(ingr, count);
            }
        });

        let plusBtn = document.createElement("button");
        plusBtn.textContent = "+";
        plusBtn.addEventListener("click", () => {
            count++;
            countSpan.textContent = count;
            doublePizzaIngredients.set(ingr, count);
        });

        controls.appendChild(minusBtn);
        controls.appendChild(countSpan);
        controls.appendChild(plusBtn);

        li.appendChild(nameSpan);
        li.appendChild(controls);
        ingredientList.appendChild(li);
    }
    
    modalWindow.style.display = 'flex'
}

function addToCart() {
    let pizza = temp[0];
    temp = [];
    let size = document.getElementById("size-input").value;
    let inputBoard = document.getElementById("board-input").value;
    let board = pizzaService.getAllPizzas().find(b  => b.name === inputBoard);

    if(board === undefined || inputBoard === "Стандартный") {
        pizza.pizzaBoard = {name : "Стандартный", price: 0, id: null};
    } else {
        pizza.pizzaBoard = board;
    }

    let res = processMultipliedCaseIngredients(doublePizzaIngredients);

    pizza.size = size;
    pizza.ingredientNames = res[0];
    pizza.price = (pizza.price + res[1]) * ((size === "25см") ? 0.9 : ((size === "35см") ? 1.2 : 1));
    
    const cart = getCart();
    cart.push(pizza);
    console.log(cart);
    localStorage.setItem("cart", JSON.stringify(cart));
    modalWindow.style.display = 'none'
}

function processMultipliedCaseIngredients(doublePizzaIngredients) {
    let ingredients = [];
    let price = 0;
    for(const [key, value] of doublePizzaIngredients) {
        if(value > 1) { 
            ingredients.push(key + ' x' + value);
            price += ingredientService.getIngredientByName(key).price * (value - 1);
        }
        else ingredients.push(key);
    }

    return [ingredients, price];
}



document.getElementById("add-AB-pizza").addEventListener("click", showModalABPizzaWindow);
document.getElementById("add-AB-pizza-to-cart").addEventListener("click", addABPizzaToCart);
let modalABPizzaWindow = document.getElementById("modal-window-AB-pizza");

function showModalABPizzaWindow() {
    let optinonA = document.getElementById("pizza-A");
    optinonA.innerHTML = '';

    let optinonB = document.getElementById("pizza-B");
    optinonB.innerHTML = '';

    for(const pizza of JSON.parse(localStorage.getItem("pizzas"))) {
        let subOption = document.createElement("option");
        subOption.value = pizza.id;
        subOption.textContent = pizza.name;
        optinonA.appendChild(subOption);
    }

    for(const pizza of JSON.parse(localStorage.getItem("pizzas"))) {
        let subOption = document.createElement("option");
        subOption.value = pizza.id;
        subOption.textContent = pizza.name;
        optinonB.appendChild(subOption);
    }

    let boardInput = document.getElementById("AB-pizza-board")
    boardInput.innerHTML = '';
    let standartOption = document.createElement("option")
    standartOption.value = "Стандартный";
    standartOption.textContent = "Стандартный";
    boardInput.appendChild(standartOption);

    for(const board of JSON.parse(localStorage.getItem("pizzaBoards"))) {
        let option = document.createElement("option");
        option.value = board.name;
        option.textContent = board.name + " " + board.price + " руб";
        boardInput.appendChild(option);
    }

    modalABPizzaWindow.style.display='flex';
}

function addABPizzaToCart() {
    let pizzaA = pizzaService.getPizzaById(parseInt(document.getElementById("pizza-A").value));
    let pizzaB = pizzaService.getPizzaById(parseInt(document.getElementById("pizza-B").value));
    let size = document.getElementById("AB-pizza-size-input").value;
    let inputBoard = document.getElementById("AB-pizza-board").value;
    let board = JSON.parse(localStorage.getItem("pizzaBoards")).find(b  => b.name === inputBoard);

    // console.log(pizzaA);
    // console.log(pizzaB);

    let ingrNames = []

    for(let ingr of pizzaA.ingredients.map(Ingredient.fromJson)) {
        ingrNames.push(ingr.name);
    }

    for(let ingr of pizzaB.ingredients.map(Ingredient.fromJson)) {
        if(!ingrNames.includes(ingr.name)) {
            ingrNames.push(ingr.name);
        }
    }

    let pizza = new Pizza();
    pizza.id = Math.floor(Math.random()*1000) + 200;  
    pizza.name = pizzaA.name + "+" + pizzaB.name;
    pizza.ingredientNames = ingrNames;
    pizza.size = size;
    pizza.pizzaBase = pizzaA.pizzaBase;
    pizza.price = (pizzaA.price + pizzaB.price) / 2;

    if(board === undefined || inputBoard === "Стандартный") {
        pizza.pizzaBoard = {name : "Стандартный", price: 0, id: null};
    } else {
        pizza.pizzaBoard = board;
    }

    pizza.price = pizza.price + board.price;

    console.log(pizza);
    const cart = getCart();
    cart.push(pizza);
    localStorage.setItem("cart", JSON.stringify(cart));
    modalABPizzaWindow.style.display = 'none'
}

let modalCustomPizzaWindow = document.getElementById("modal-window-custom-pizza");
document.getElementById("add-custom-pizza").addEventListener("click", showModalCustomPizzaWindow);
document.getElementById("add-custom-pizza-to-cart").addEventListener("click", addCustomPizzaToCart);

let customPizzaIngredients = new Map();;

function showModalCustomPizzaWindow() {
    modalCustomPizzaWindow.style.display = 'flex';
    let nameInput = document.getElementById("custom-pizza-name");
    // nameInput.innerHTML = '';

    let ingredientList = document.getElementById("ingredient-list");
    ingredientList.innerHTML = '';

    let ingredients = ingredientService.ingredients;

    for(let i = 0; i < ingredients.length; i++) {
        let ingr = ingredients[i];
        const li = document.createElement("li");

        let count = 0;

        let nameSpan = document.createElement("span");
        nameSpan.textContent = ingr.name;

        let controls = document.createElement("div");
        controls.className = "counter-controls";

        let countSpan = document.createElement("span");
        countSpan.className = "counter";
        countSpan.textContent = count;

        let minusBtn = document.createElement("button");
        minusBtn.textContent = "-";
        minusBtn.addEventListener("click", () => {
            if (count > 0) {
                count--;
                countSpan.textContent = count;
                if(count === 0) {
                    customPizzaIngredients.delete(ingr.name);
                } else {
                    customPizzaIngredients.set(ingr.name, count);
                }
            }
        });

        let plusBtn = document.createElement("button");
        plusBtn.textContent = "+";
        plusBtn.addEventListener("click", () => {
            count++;
            countSpan.textContent = count;
            customPizzaIngredients.set(ingr.name, count);
        });

        controls.appendChild(minusBtn);
        controls.appendChild(countSpan);
        controls.appendChild(plusBtn);

        li.appendChild(nameSpan);
        li.appendChild(controls);
        ingredientList.appendChild(li);
    }


    let boardInput = document.getElementById("custom-pizza-board")
    boardInput.innerHTML = '';
    let standartOption = document.createElement("option")
    standartOption.value = "Стандартный";
    standartOption.textContent = "Стандартный";
    boardInput.appendChild(standartOption);

    for(const board of pizzaBoardService.getAllPizzaBoards()) {
        let option = document.createElement("option");
        option.value = board.id;
        option.textContent = board.name + " " + board.price + " руб";
        boardInput.appendChild(option);
    }

    let baseInput = document.getElementById("custom-pizza-base")
    baseInput.innerHTML = '';

    for(const base of pizzaBaseService.getAllPizzaBases()) {
        let option = document.createElement("option");
        option.value = base.type;
        option.textContent = base.type + " " + base.price + " руб";
        baseInput.appendChild(option);
    }

    modalCustomPizzaWindow.style.display='flex';
}

function addCustomPizzaToCart() {
    let nameInput = document.getElementById("custom-pizza-name").value;
    if(nameInput === null || nameInput.trim() === '') {
        alert("Добавьте название для Вашей пиццы");
    }

    if(customPizzaIngredients.size < 1) {
        alert("Добавьте больше ингредиентов");
    }

    let size = document.getElementById("custom-pizza-size-input").value;
    let inputBoardId = parseInt(document.getElementById("custom-pizza-board").value);
    let inputBase = document.getElementById("custom-pizza-base").value;
    let board = pizzaBoardService.getAllPizzaBoards().find(b  => b.id === inputBoardId);
    let pizzaBase = pizzaBaseService.getPizzaBaseByType(inputBase);

    let ingrNames = []

    for(const [key, value] of customPizzaIngredients) {
        let name = '';
        if(value > 1) {
            name = key + ' x' + value;
        } else {
            name = key;
        }
        ingrNames.push(name);
    }

    let pizza = new Pizza();
    pizza.id = Math.floor(Math.random()*1000) + 200;
    pizza.name = nameInput;
    pizza.ingredientNames = ingrNames;
    pizza.size = size;
    pizza.pizzaBase = pizzaBase;
    
    if(board === undefined) {
        pizza.pizzaBoard = {name : "Стандартный", price: 0, id: null};
    } else {
        pizza.pizzaBoard = board;
    }

    pizza.price = (evalPrice(customPizzaIngredients) + pizzaBase.price + pizza.pizzaBoard.price) * ((size === "25см") ? 0.9 : ((size === "35см") ? 1.2 : 1));

    console.log(pizza);
    const cart = getCart();
    cart.push(pizza);
    localStorage.setItem("cart", JSON.stringify(cart));

    customPizzaIngredients.clear();
    modalCustomPizzaWindow.style.display = 'none'
}

function evalPrice(customPizzaIngredients) {
    let price = 0;
    for(const [key, value] of customPizzaIngredients) {
        const ingr = ingredientService.getIngredientByName(key);
        price += ingr.price * value;
    }
    return price;
}