import { css } from '@emotion/react';

import theme from '@/styles/theme';

const layout = css`
  min-width: 320px;
  width: 100%;
  height: 30.2rem;
  background-color: ${theme.colors.white};
  box-shadow: 2px 2px 2px 2px ${theme.colors.shadow10};
  border-radius: 8px;
`;

const title = css`
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 1rem 1.25rem;
  font-size: 1.4rem;
  border-bottom: 1px solid ${theme.colors.gray300};
`;

const jobListWrapper = css`
  display: flex;
  flex-direction: column;
  height: 80%;
  align-items: center;
  padding: 24px;
  overflow-y: scroll;

  div + div {
    margin-top: 16px;
  }
`;

const jobList = css`
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  padding: 12px 16px;
  background-color: ${theme.colors.gray100};
  border-radius: 8px;
  color: ${theme.colors.black};
  font-size: 500;
  font-size: 18px;
`;

const newJobButton = css`
  width: auto;
  height: 2rem;
  padding: 6px 10px;
  font-weight: 500;
  font-size: 1rem;
  padding: 0 12px;

  margin: 0;
`;

const updateButton = css`
  width: auto;
  height: auto;
  padding: 8px 12px;
  font-size: 12px;
  margin: 0 8px;
`;

const deleteButton = css`
  width: auto;
  height: auto;
  padding: 8px 12px;
  font-size: 12px;
  margin: 0;
  background-color: ${theme.colors.red};
`;

const empty = css`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  img {
    max-width: 100px;
    margin-bottom: 12px;
  }
`;
const styles = { layout, title, jobListWrapper, jobList, newJobButton, updateButton, deleteButton, empty };

export default styles;
