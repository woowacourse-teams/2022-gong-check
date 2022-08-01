/// <reference types="cypress" />
// ***********************************************

const SECTIONS = [
  {
    id: 1,
    name: '트랙룸',
    tasks: [
      {
        id: 1,
        name: '책상 청소',
        checked: false,
      },
    ],
  },
  {
    id: 2,
    name: '굿샷강의장',
    tasks: [
      {
        id: 2,
        name: '의자 청소',
        checked: false,
      },
    ],
  },
];

Cypress.Commands.add<any>('setToken', () => {
  localStorage.setItem('token', 'json_web_token');
});

// mockAPIs
Cypress.Commands.addAll({
  spaceEnter() {
    const PASSWORD = '1234';

    cy.intercept('POST', 'http://localhost:8080/api/hosts/1/enter', (request: any) => {
      request.reply(
        request.body.password === PASSWORD
          ? {
              statusCode: 200,
            }
          : {
              statusCode: 401,
            }
      );
    }).as('spaceEnter');
  },

  getSpaces() {
    cy.intercept('GET', 'http://localhost:8080/api/spaces', (request: any) => {
      request.reply({
        statusCode: 200,
        body: {
          spaces: [
            {
              id: 1,
              name: '잠실 캠퍼스',
              imageUrl: 'https://velog.velcdn.com/images/cks3066/post/258f92c1-32be-4acb-be30-1eb64635c013/image.jpg',
            },
            {
              id: 2,
              name: '선릉 캠퍼스',
              imageUrl: 'https://velog.velcdn.com/images/cks3066/post/28a9d0e5-d585-42e4-bc9e-458e439e2f4f/image.jpg',
            },
          ],
        },
      });
    }).as('getSpaces');
  },

  getJobs() {
    cy.intercept('GET', 'http://localhost:8080/api/spaces/1/jobs', (request: any) => {
      request.reply({
        statusCode: 200,
        body: {
          jobs: [
            {
              id: 1,
              name: '청소',
            },
            {
              id: 2,
              name: '마감',
            },
          ],
        },
      });
    }).as('getJobs');
  },

  getSpaceInfo() {
    cy.intercept('GET', 'http://localhost:8080/api/spaces/1', (request: any) => {
      request.reply({
        statusCode: 200,
        body: {
          id: 1,
          name: '잠실 캠퍼스',
          imageUrl: 'https://velog.velcdn.com/images/cks3066/post/258f92c1-32be-4acb-be30-1eb64635c013/image.jpg',
        },
      });
    }).as('getSpaceInfo');
  },

  getRunningTaskActive_true(jobId: number) {
    cy.intercept('GET', `http://localhost:8080/api/jobs/${jobId}/active`, (request: any) => {
      request.reply({
        statusCode: 200,
        body: {
          active: true,
        },
      });
    }).as('getRunningTaskActive_true');
  },

  getRunningTaskActive_false(jobId: number) {
    cy.intercept('GET', `http://localhost:8080/api/jobs/${jobId}/active`, (request: any) => {
      request.reply({
        statusCode: 200,
        body: {
          active: false,
        },
      });
    }).as('getRunningTaskActive_false');
  },

  postNewRunningTasks(jobId: number) {
    cy.intercept('POST', `http://localhost:8080/api/jobs/${jobId}/runningTasks/new`, (request: any) => {
      request.reply(
        jobId === 1
          ? {
              statusCode: 200,
            }
          : {
              statusCode: 401,
              body: {
                message: '작업이 존재하지 않습니다.',
              },
            }
      );
    }).as('postNewRunningTasks');
  },

  getRunningTasks() {
    cy.intercept('GET', 'http://localhost:8080/api/jobs/1/runningTasks', (request: any) => {
      request.reply({
        statusCode: 200,
        body: { sections: SECTIONS },
      });
    }).as('getRunningTasks');
  },

  postCheckTask(taskId: number) {
    cy.intercept('POST', `http://localhost:8080/api/tasks/${taskId}/flip`, (request: any) => {
      SECTIONS.forEach(section =>
        section.tasks.forEach(task => {
          if (task.id === taskId) {
            task.checked = true;
          }
        })
      );
      request.reply({
        statusCode: 200,
      });
    }).as('postCheckTask');
  },

  postJobComplete() {
    cy.intercept('POST', `http://localhost:8080/api/jobs/1/complete`, (request: any) => {
      request.reply({
        statusCode: 200,
      });
    }).as('postJobComplete');
  },
});
