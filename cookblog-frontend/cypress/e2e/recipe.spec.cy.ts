describe('Recipe Tests', () => {
  let categoryName: string;

  before(() => {
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
      cy.wait(1000);

      cy.contains(recipe.title).should('exist');
      cy.contains(recipe.description).should('exist');
    });
  });

  it('Should read recipe', () => {
    cy.fixture('recipe').then((recipe) => {
      cy.visit('/');
      clickNextUntilDisabled();
      cy.get('.button').contains('Read').click({ force: true });
      cy.wait(1000);
      cy.contains(recipe.title).should('exist');
      cy.contains(recipe.description).should('exist');
      cy.contains(recipe.instructions).should('exist');
      cy.contains(recipe.preparationTimeInMinutes).should('exist');
      cy.contains(recipe.calorificValue).should('exist');
      cy.contains(recipe.portions).should('exist');
    });
  });

  const clickNextButton = () => {
    cy.get('mat-icon[aria-hidden="true"][data-mat-icon-type="font"]')
      .contains('navigate_next')
      .click({ force: true });
  };

  it('Should browse recipes', () => {
    cy.visit('/');
    cy.wait(1000);
    cy.contains('Dolores porro et maiores deserunt sit consequatur.').should(
      'exist',
    );
    cy.contains(
      'Explicabo non et nam amet. Vitae laboriosam ipsa itaque. Et molestiae voluptatibus minima sint.',
    ).should('exist');

    clickNextButton();
    cy.wait(1000);
    cy.contains(
      'Rerum porro tempora voluptas ipsum et facilis quibusdam.',
    ).should('exist');
    cy.contains(
      'Omnis error aperiam. Veritatis id ullam sit numquam qui et nulla. Odio maxime adipisci velit. Vitae ut corrupti est provident. Aliquid non temporibus nihil dolores.',
    ).should('exist');

    clickNextButton();
    cy.wait(1000);
    cy.contains('Deleniti eligendi reiciendis ut vel.').should('exist');
    cy.contains(
      'Quisquam laboriosam omnis mollitia dolorem in. Atque atque ipsam recusandae. Laudantium quo minus. Tempora omnis qui molestiae dolores ea ea sunt. Vero optio id odio.',
    ).should('exist');
  });

  it('Should edit recipe', () => {
    cy.fixture('recipe').then((recipe) => {
      cy.visit('/');
      clickNextUntilDisabled();
      cy.get('.button').contains('Edit').click({ force: true });
      cy.wait(1000);
      cy.get('input[data-test-id="title"]').type(recipe.title + 'edited');
      cy.get('textarea[data-test-id="description"]').type(
        recipe.description + 'edited',
      );
      cy.get('textarea[data-test-id="instructions"]').type(
        recipe.instructions + 'edited',
      );
      cy.get('input[data-test-id="image"]').type(recipe.image + 'edited');

      cy.get('mat-select[data-test-id="category"]').click();
      cy.get('mat-option').contains(categoryName).click();

      cy.get('input[data-test-id="preparationTimeInMinutes"]').type(
        recipe.preparationTimeInMinutes,
        { force: true },
      );

      cy.get('input[data-test-id="portions"]').type(recipe.portions + 1);
      cy.get('input[data-test-id="calorificValue"]').type(
        recipe.calorificValue + 1,
        {
          force: true,
        },
      );
      cy.get('input[data-test-id="ingredient-name"]').type(
        recipe.calorificValue + 1,
      );

      cy.get('button[aria-label="save"]').click();
      cy.visit('/');

      clickNextUntilDisabled();
      cy.get('.button').contains('Read').click({ force: true });
      cy.wait(1000);
      cy.contains(recipe.title).should('exist');
      cy.contains(recipe.description).should('exist');
      cy.contains(recipe.instructions).should('exist');
      cy.contains(recipe.preparationTimeInMinutes).should('exist');
      cy.contains(recipe.calorificValue).should('exist');
      cy.contains(recipe.portions).should('exist');
    });
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
});
