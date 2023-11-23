describe('Recipe Tests', () => {
  let categoryName: string;

  before(() => {
    // Dodanie nowej kategorii przed rozpoczęciem testów
    cy.fixture('category').then((category) => {
      cy.visit('/');
      cy.get('button[aria-label="menu"]').click();
      cy.wait(1000);
      cy.contains('Create category').click();
      cy.get('input[data-test-id="name"]').type(category.name);
      cy.get('button[aria-label="save"]').click();
      cy.visit('/');
      cy.get('button[aria-label="menu"]').click();
      cy.wait(1000);
      categoryName = category.name;
    });
  });

  const clickNextUntilDisabled = () => {
    cy.get('mat-icon[aria-hidden="true"][data-mat-icon-type="font"]')
      .contains('navigate_next')
      .then((nextButton) => {
        const isDisabled = nextButton.closest('button').prop('disabled');
        if (!isDisabled) {
          cy.wrap(nextButton).click({ force: true });
          cy.wait(1000);
          clickNextUntilDisabled();
        }
      });
  };

  it('Should create new recipe', () => {
    // Dodanie nowego przepisu
    cy.fixture('recipe').then((recipe) => {
      cy.visit('/');
      cy.wait(1000);
      cy.get('a[aria-label="create recipe"]').click();
      cy.wait(1000);
      cy.get('input[data-test-id="title"]').type(recipe.title);
      cy.get('textarea[data-test-id="description"]').type(recipe.description);
      cy.get('textarea[data-test-id="instructions"]').type(recipe.instructions);
      cy.get('input[data-test-id="image"]').type(recipe.image);

      cy.get('mat-select[data-test-id="category"]').click();
      cy.get('mat-option').contains(categoryName).click();

      cy.get('input[data-test-id="preparationTimeInMinutes"]').type(
        recipe.preparationTimeInMinutes,
        { force: true },
      );

      cy.get('input[data-test-id="portions"]').type(recipe.portions);
      cy.get('input[data-test-id="calorificValue"]').type(
        recipe.calorificValue,
        { force: true },
      );
      cy.get('input[data-test-id="ingredient-name"]').type(
        recipe.calorificValue,
      );

      cy.get('button[aria-label="save"]').click();
      cy.visit('/');
      cy.wait(1000);

      clickNextUntilDisabled();

      cy.contains(recipe.title).should('exist');
      cy.contains(recipe.description).should('exist');
    });
  });

  it('Should browse recipes', () => {
    cy.visit('/');
  });

  it('Should delete recipe ', () => {
    cy.fixture('recipe').then((recipe) => {
      cy.visit('/');
      clickNextUntilDisabled();
      cy.contains('.card', recipe.title).within(() => {
        cy.get('.button[color="warn"]').click();
      });

      cy.get('[data-test-id="confirm-button"]').click();

      cy.wait(1000);
      cy.visit('/');
      clickNextUntilDisabled();
      cy.contains('.card', recipe.title).should('not.exist');
    });
  });

  it('There should be recipes', () => {
    cy.visit('/');
  });

  it('Should edit recipe', () => {
    cy.visit('/');
  });

  it('Should read recipe', () => {
    cy.visit('/');
  });
});
