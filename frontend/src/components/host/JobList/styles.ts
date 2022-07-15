import { css } from '@emotion/react';

import theme from '@/styles/theme';

const layout = css`
  width: 40vw;
  height: 40vh;
  background-color: white;
`;

const title = css`
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 24px;
  height: 20%;
  font-size: 18px;
  border-bottom: 1px solid ${theme.colors.lightGray};
  color: ${theme.colors.blackHost};
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
    background-color: ${theme.colors.lightGrayHost};
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
  background-color: ${theme.colors.lightGrayHost};
  border-radius: 8px;
  color: ${theme.colors.blackHost};
`;

const slackButton = css`
  width: auto;
  height: auto;
  padding: 6px 10px;
  font-size: 12px;
  margin: 0 12px;
  background-color: ${theme.colors.white};
  color: ${theme.colors.black};
  border: 1px solid ${theme.colors.gray};

  img {
    height: 10px;
    margin-right: 6px;
  }
`;

const newJobButton = css`
  width: auto;
  height: auto;
  padding: 6px;
  font-size: 12px;
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
  background-color: tomato;
`;

const styles = { layout, title, jobListWrapper, jobList, slackButton, newJobButton, updateButton, deleteButton };

export default styles;
