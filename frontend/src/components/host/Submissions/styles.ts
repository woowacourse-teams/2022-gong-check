import { css } from '@emotion/react';

import theme from '@/styles/theme';

const layout = css`
  background: ${theme.colors.white};
  border-radius: 8px;
  font-size: 16px;
`;

const header = css`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 1.25em;
  p {
    font-size: 1.3em;
  }
`;

const detailButton = css`
  font-size: 1em;
  width: 5em;
  margin: 0;
`;

const table = css`
  width: 100%;
  padding: 1em;
  border-collapse: collapse;
  display: block;

  thead {
    background-color: ${theme.colors.lightGray};
    color: ${theme.colors.shadow30};
    box-shadow: 0 1px 4px 0px ${theme.colors.shadow30};
    width: 100%;
  }

  tbody {
    width: 100%;
    display: block;
    overflow-y: scroll;
    height: 12.5em;

    ::-webkit-scrollbar {
      width: 0.3em;
    } /* 스크롤 바 */

    ::-webkit-scrollbar-track {
      /* background-color: #fff; */
    } /* 스크롤 바 밑의 배경 */

    ::-webkit-scrollbar-thumb {
      background: #ddd;
      border-radius: 8px;
    } /* 실질적 스크롤 바 */

    ::-webkit-scrollbar-thumb:hover {
      background: #404040;
    } /* 실질적 스크롤 바 위에 마우스를 올려다 둘 때 */

    ::-webkit-scrollbar-thumb:active {
      background: #808080;
    } /* 실질적 스크롤 바를 클릭할 때 */

    ::-webkit-scrollbar-button {
      display: none;
    } /* 스크롤 바 상 하단 버튼 */

    tr {
      border-bottom: 1px solid black;
    }
    tr:nth-last-of-type(1) {
      border-bottom: none;
    }
  }

  th,
  td {
    padding: 1em;
    min-width: 6em;
    max-width: 6em;

    word-wrap: break-word;
    text-align: left;
    &:nth-of-type(3) {
      min-width: 15em;
      max-width: 15em;
    }
  }
`;

const styles = { layout, header, detailButton, table };

export default styles;
