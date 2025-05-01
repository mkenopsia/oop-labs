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
  
class Pizza {
    constructor(id, name, ingredients, pizzaBase, price) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.pizzaBase = pizzaBase;
        this.price = price;
    }
  
    static fromJson(json) {
        return new Pizza(
        json.id,
        json.name,
        json.ingredients.map(Ingredient.fromJson),
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
    constructor(baseUrl = 'http://localhost:8080/pizzamaker') {
        this.baseUrl = baseUrl;
        this.pizzas = [];
    }
  
    async fetchPizzas() {
        try {
            const response = await fetch(`${this.baseUrl}/pizzas`);
        
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

document.addEventListener('DOMContentLoaded', async () => {
    const pizzaService = new PizzaApiService();

    try {
    const pizzas = await pizzaService.fetchPizzas();
    console.log('Загружены пиццы:', pizzas);

    renderPizzas(pizzas, pizzaService);

    } catch (error) {
    console.error('Не удалось загрузить меню:', error);
    }
});

function renderPizzas(pizzas, pizzaService) {
    const container = document.getElementById('pizzas-container');
    if (!container) return;

    container.innerHTML = pizzas.map(pizza => `
    <div class="pizza-card" data-id="${pizza.id}">
        <h3>${pizza.name}</h3>
        <div class="price">${pizza.price.toFixed(2)} ₽</div>
        <div class="base">Основа: ${pizza.pizzaBase.type}</div>
        <div class="ingredients">
        <h4>Ингредиенты:</h4>
        <ul>
            ${pizza.ingredients.map(ing => 
            `<li>${ing.name}</li>`
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
            if (pizza) {
                addToCart(pizza);
            }
        });
    });
}

function addToCart(pizza) {
  console.log('Добавлено в корзину:', pizza);
}