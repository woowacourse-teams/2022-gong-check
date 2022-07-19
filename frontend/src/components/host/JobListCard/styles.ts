import { css } from '@emotion/react';

import theme from '@/styles/theme';

const layout = css`
  width: 36rem;
  min-height: 25rem;
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
  border-bottom: 1px solid ${theme.colors.gray200};
`;

const jobListWrapper = css`
  display: flex;
  flex-direction: column;
  height: 80%;
  align-items: center;
  padding: 24px;
  overflow-y: scroll;
  -ms-overflow-style: none;

  ::-webkit-scrollbar {
    display: hidden;
    width: 4px;
  }
  ::-webkit-scrollbar-thumb {
    background-color: ${theme.colors.gray100};
    height: 4px;
    border: 100%;
  }
  ::-webkit-scrollbar-track {
    display: none;
  }

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
  color: ${theme.colors.gray800};
`;

const slackButton = css`
  width: auto;
  height: 2rem;
  padding: 0 12px;
  font-weight: 500;
  font-size: 1rem;
  margin: 0 12px;
  background-color: ${theme.colors.white};
  color: ${theme.colors.black};
  border: 1px solid ${theme.colors.gray400};

  img {
    height: 12px;
    width: 12px;
    margin-right: 6px;
  }
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

const styles = { layout, title, jobListWrapper, jobList, slackButton, newJobButton, updateButton, deleteButton };

export default styles;
