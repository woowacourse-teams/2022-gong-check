const WRONG_PASSWORD = '0000';
const CORRECT_PASSWORD = '1234';

const PAGE = {
  PASSWORD: 'http://localhost:3000/enter/1/pwd',
  SPACE_LIST: 'http://localhost:3000/enter/1/spaces',
  JOB_LIST: 'http://localhost:3000/enter/1/spaces/1',
  TASK_LIST: 'http://localhost:3000/enter/1/spaces/1/1',
};

describe('사용자, - 비밀번호 입력 페이지', () => {
  beforeEach(() => {
    cy.visit(PAGE.PASSWORD);
    cy.spaceEnter();
    cy.getSpaces();
  });

  it('사용자가 잘못된 비밀번호를 입력하면, API 호출이 실패하고 토스트바로 안내해준다.', () => {
    cy.get('input').type(WRONG_PASSWORD);

    cy.get('button').click();

    cy.get('@spaceEnter').then(({ response }: any) => {
      cy.get('#toast > div').should('be.contain', '비밀번호를 확인해주세요.');
      expect(response.statusCode).to.equal(401);
    });
  });

  it('사용자가 올바른 비밀번호를 입력하면, API 호출이 성공하고 공간 선택 페이지로 이동한다.', () => {
    cy.get('input').type(CORRECT_PASSWORD);

    cy.get('button').click();

    cy.get('@spaceEnter').then(({ response }: any) => {
      expect(response.statusCode).to.equal(200);
    });
  });
});

describe('사용자, - 공간 선택 페이지', () => {
  beforeEach(() => {
    cy.setToken();
    cy.visit(PAGE.SPACE_LIST);
    cy.spaceEnter();
    cy.getSpaces();
    cy.getJobs();
    cy.getSpaceInfo();
  });

  it('사용자가 공간을 클릭하면, 공간의 업무 선택 페이지로 이동한다.', () => {
    cy.get('[class$=-spaceCard]').first().click();

    cy.url().should('eq', PAGE.JOB_LIST);
  });
});

describe('사용자 - 업무 선택 페이지', () => {
  beforeEach(() => {
    cy.setToken();
    cy.visit(PAGE.JOB_LIST);
    cy.getJobs();
    cy.getSpaceInfo();
    cy.getRunningTasks();
  });

  it('사용자가 작업이 진행중이지 않은 업무를 클릭하면, confirm 창이 노출된다.', () => {
    cy.getRunningTaskActive_false(1);
    cy.postNewRunningTasks(1);
    cy.get('[class$=-jobCard]').first().click();

    cy.on('window:confirm', text => {
      expect(text).to.contains('진행중인 체크리스트가 없습니다. 새롭게 생성하시겠습니까?');
      return false;
    });
  });

  it('사용자가 작업이 진행중인 업무를 클릭하면, 해당 체크리스트 페이지로 이동한다.', () => {
    cy.getRunningTaskActive_true(1);
    cy.postNewRunningTasks(1);
    cy.get('[class$=-jobCard]').first().click();

    cy.url().should('eq', PAGE.TASK_LIST);
  });

  it('사용자가 새로운 체크리스트 생성시, 업무 목록이 존재하지 않으면 토스트바로 안내해준다.', () => {
    cy.getRunningTaskActive_false(2);
    cy.postNewRunningTasks(2);
    cy.get('[class$=-jobCard]').last().click();

    cy.on('window:confirm', text => {
      expect(text).to.contains('진행중인 체크리스트가 없습니다. 새롭게 생성하시겠습니까?');
      return true;
    });

    cy.get('@postNewRunningTasks').then(({ response }: any) => {
      cy.get('#toast > div').should('be.contain', '작업이 존재하지 않습니다.');
      expect(response.statusCode).to.equal(401);
    });
  });
});

describe('사용자 - 체크리스트 체크 페이지', () => {
  beforeEach(() => {
    cy.setToken();
    cy.visit(PAGE.TASK_LIST);
    cy.getSpaceInfo();
    cy.getRunningTasks();
    cy.postJobComplete();
  });

  it('사용자가 체크 박스를 클릭하면, 해당 사항이 체크표시된다.', () => {
    cy.postCheckTask(1);
    cy.get('label').first().click();

    cy.get('label').first().should('have.css', 'background-color', 'rgb(126, 217, 87)');
  });

  it('사용자가 모든 체크 박스를 클릭하면, 제출 버튼이 활성화된다.', () => {
    cy.postCheckTask(1);
    cy.get('label').first().click();
    cy.postCheckTask(2);
    cy.get('label').last().click();

    cy.get('button').should('not.be.disabled');
  });

  it('사용자가 제출 버튼을 클릭하면, 제출 모달이 노출된다.', () => {
    cy.postCheckTask(1);
    cy.get('label').first().click();
    cy.postCheckTask(2);
    cy.get('label').last().click();

    cy.get('button').click();

    cy.get('#modal > div').should('be.visible');
  });

  it('사용자가 제출 모달에서 제출자를 입력하고 버튼을 누르면, 제출 alert가 발생한다. ', () => {
    cy.postCheckTask(1);
    cy.get('label').first().click();
    cy.postCheckTask(2);
    cy.get('label').last().click();

    cy.get('button').click();

    cy.get('#modal input').type('coke');
    cy.get('#modal button').click();

    cy.on('window:alert', text => {
      expect(text).to.contains('제출 되었습니다.');
    });
  });
});