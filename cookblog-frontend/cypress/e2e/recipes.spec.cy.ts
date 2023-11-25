describe('Recipes Tests', () => {
  it('Should view recipes assigned to categories', () => {
    cy.visit('/categories/2');
    cy.wait(1000);
    cy.get('mat-card').first().should('exist');
  });

  it('Should set the category of a recipe', () => {
    cy.visit('/recipes/edit/1');
    cy.wait(1000);
    cy.get('mat-select[data-test-id="category"]')
      .click()
      .get('mat-option')
      .last()
      .click();
    cy.get('button[aria-label="save"]').click();
    cy.wait(1000);
    cy.visit('/recipes/edit/1');
    cy.wait(1000);
    cy.get('mat-select[data-test-id="category"]')
      .click()
      .get('mat-option')
      .last()
      .should('have.attr', 'aria-selected', 'true');
  });

  it('Should add information about calorific value', () => {
    cy.fixture('recipe').then((recipe) => {
      cy.visit('/recipes/edit/1');
      cy.wait(1000);
      cy.get('input[data-test-id="calorificValue"]')
        .clear()
        .type(recipe.calorificValue);
      cy.get('button[aria-label="save"]').click();
      cy.wait(1000);
      cy.visit('/recipes/edit/1');
      cy.wait(1000);
      cy.get('input[data-test-id="calorificValue"]').should(
        'have.value',
        recipe.calorificValue,
      );
    });
  });

  it('Should add information about preparation time', () => {
    cy.fixture('recipe').then((recipe) => {
      cy.visit('/recipes/edit/1');
      cy.wait(1000);
      cy.get('input[data-test-id="preparationTimeInMinutes"]')
        .clear()
        .type(recipe.preparationTimeInMinutes);
      cy.get('button[aria-label="save"]').click();
      cy.wait(1000);
      cy.visit('/recipes/edit/1');
      cy.wait(1000);
      cy.get('input[data-test-id="preparationTimeInMinutes"]').should(
        'have.value',
        recipe.preparationTimeInMinutes,
      );
    });
  });

  it('Should add information about portions', () => {
    cy.fixture('recipe').then((recipe) => {
      cy.visit('/recipes/edit/1');
      cy.wait(1000);
      cy.get('input[data-test-id="portions"]').clear().type(recipe.portions);
      cy.get('button[aria-label="save"]').click();
      cy.wait(1000);
      cy.visit('/recipes/edit/1');
      cy.wait(1000);
      cy.get('input[data-test-id="portions"]').should(
        'have.value',
        recipe.portions,
      );
    });
  });

  it('Should add list od ingredients to recipe', () => {
    cy.fixture('recipe').then((recipe) => {
      cy.visit('/recipes/edit/1');
      cy.wait(1000);
      cy.contains('Add ingredient').click();
      cy.get('input[data-test-id="ingredient-name"]')
        .last()
        .type(recipe.ingredientName);
      cy.get('input[data-test-id="ingredient-amount"]')
        .last()
        .clear()
        .type(recipe.amount);
      cy.get('button[aria-label="save"]').click();
      cy.wait(1000);
      cy.visit('/recipes/edit/1');
      cy.wait(1000);
      cy.get('input[data-test-id="ingredient-name"]')
        .eq(0)
        .should('have.value', recipe.ingredientName);
      cy.get('input[data-test-id="ingredient-amount"]')
        .eq(0)
        .should('have.value', recipe.amount);
    });
  });
});
