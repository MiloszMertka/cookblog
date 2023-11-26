describe('Comment Tests', () => {
  it('Should create a new comment', () => {
      cy.fixture('comment').then((comment) => {
        const authorName = "Author";
        cy.visit('/recipes/1');
        cy.wait(1000);

        cy.get('input[data-test-id="author"]').type(authorName);
        cy.get('textarea[data-test-id="content"]').type(comment.commentContent);
        cy.contains('Add comment').click();

        cy.wait(1000);
        cy.visit('/recipes/1');
        cy.wait(1000);

        cy.contains(comment.commentContent).should('exist');
      });
   });

  it('Should delete a comment', () => {
    cy.fixture('comment').then((comment) => {
      cy.visit('/recipes/1');
      cy.wait(1000);

      cy.get('button[aria-label="delete comment"]')
        .first()
        .click();

      cy.wait(1000);
      cy.visit('/recipes/1');
      cy.wait(1000);

      cy.contains(comment.commentContent).should('not.exist');
    });
  });
});


