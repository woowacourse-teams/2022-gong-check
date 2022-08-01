import 'cypress-react-selector';

const WRONG_PASSWORD = '0000';
const CORRECT_PASSWORD = '1234';

describe('사용자, - 비밀번호 입력 페이지', () => {
  before(() => {
    cy.visit('http://localhost:3000/enter/1/spaces');
  });

  it('사용자가 잘못된 비밀번호를 입력하면 토스트바로 안내해준다.', () => {
    cy.get('input').type(WRONG_PASSWORD);
    cy.get('button').click();
    // cy.request('POST', 'http://localhost:8080/api/hosts/1/enter').as('enter');
    // cy.get('@enter').then((response: any) => {
    //   cy.log(response);
    //   expect(response.status).to.eq(200);
    //   // assert.isArray(todos, 'Todos Response is an array');
    // });

    cy.request('POST', 'http://localhost:8080/api/hosts/1/enter', { password: WRONG_PASSWORD }).then(
      (response: any) => {
        cy.log(response);
        expect(response.status).to.eq(401);
        // assert.isArray(todos, 'Todos Response is an array');
      }
    );
    // cy.get('#toast > div').should('be.visible');
  });
});

// describe('유저 - 공간 선택 페이지', () => {
//   it('Visits the Kitchen Sink', () => {
//     cy.visit('http://localhost:3000/enter/1/spaces');
//   });
// });

// describe('유저 - 업무 선택 페이지', () => {
//   it('Visits the Kitchen Sink', () => {
//     cy.visit('http://localhost:3000/enter/1/spaces');
//   });
// });

// describe('유저 - 체크리스트 체크 페이지', () => {
//   it('Visits the Kitchen Sink', () => {
//     cy.visit('http://localhost:3000/enter/1/spaces');
//   });
// });
