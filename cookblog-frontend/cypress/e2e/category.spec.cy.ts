describe('Category Tests', () => {
  it('Should create new category', () => {
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
      cy.contains(category.name).should('exist');
    });
  });

  it('Should get details of category', () => {
    cy.fixture('category').then((category) => {
      cy.visit('/');
      cy.get('button[aria-label="menu"]').click();
      cy.wait(1000);
      cy.contains(category.name).should('exist').click();
      cy.wait(1000);
      cy.url().should('include', '/categories');
    });
  });

  it('Should update category', () => {
    cy.fixture('category').then((category) => {
      cy.visit('/');
      cy.get('button[aria-label="menu"]').click();
      cy.wait(1000);
      cy.contains(category.name)
        .parent()
        .within(() => cy.get('a[aria-label="edit category"]').click());
      cy.wait(1000);
      cy.get('input[data-test-id="name"]').type(category.updateName);
      cy.get('button[aria-label="save"]').click();
      cy.reload();
      cy.get('button[aria-label="menu"]').click();
      cy.wait(1000);
      cy.contains(category.updateName).should('exist');
    });
  });

  it('Should delete category ', () => {
    cy.fixture('category').then((category) => {
      cy.visit('/');
      cy.get('button[aria-label="menu"]').click();
      cy.wait(1000);
      cy.contains(category.name)
        .parent()
        .within(() => cy.get('button[aria-label="delete category"]').click());
      cy.wait(1000);
      cy.contains('Are you sure you want to delete this category?')
        .parent()
        .within(() => cy.contains('Delete').click());
      cy.contains(category.name).should('not.exist');
    });
  });
});
