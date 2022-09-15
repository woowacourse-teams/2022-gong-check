import { css } from '@emotion/react';

import theme from '@/styles/theme';

const layout = ({ isFullSize }: { isFullSize: boolean }) => css`
  min-width: 320px;
  background-color: ${theme.colors.white};
  border-radius: 8px;
  font-size: 16px;
  box-shadow: 2px 2px 2px 2px ${theme.colors.shadow10};
  height: 360px;

  ${isFullSize &&
  css`
    margin-top: 5rem;
    height: 480px;
  `}

  @media screen and (min-width: 1024px) {
    width: 100%;
  }

  @media screen and (max-width: 1023px) {
    width: 90%;
  }
`;

const header = css`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 24px;
  border-bottom: 1px solid ${theme.colors.gray300};
  p {
    font-size: 1.2rem;
  }
`;

const detailButton = css`
  width: 5rem;
  height: 2rem;
  margin: 0;
  font-size: 0.9rem;
`;

const table = ({ isFullSize }: { isFullSize: boolean }) => css`
  width: 100%;
  padding: 1em;
  border-collapse: collapse;
  display: block;

  thead {
    background-color: ${theme.colors.gray200};
    color: ${theme.colors.shadow30};
    box-shadow: 0 1px 4px 0px ${theme.colors.shadow30};
    width: 100%;
    display: block;
  }

  tbody {
    display: flex;
    flex-direction: column;
    justify-content: start;
    height: 100%;
    width: 100%;
    overflow-y: ${isFullSize ? 'scroll' : 'hidden'};
    height: ${isFullSize ? '50vh' : '12.5em'};

    ::-webkit-scrollbar {
      width: 0.3em;
    }

    ::-webkit-scrollbar-thumb {
      background: ${theme.colors.shadow20};
      border-radius: 8px;
    }

    ::-webkit-scrollbar-thumb:hover {
      background: ${theme.colors.shadow60};
    }

    ::-webkit-scrollbar-thumb:active {
      background: ${theme.colors.shadow80};
    }

    ::-webkit-scrollbar-button {
      display: none;
    }

    tr {
      border-bottom: 1px solid ${theme.colors.shadow10};
    }

    tr:nth-last-of-type(1) {
      border-bottom: none;
    }
  }

  th,
  td {
    padding: 1em;
    height: 10px;
    width: 20vw;
    min-width: 80px;
    word-wrap: break-word;
    text-align: left;
    &:nth-of-type(3) {
      width: 30vw;
      min-width: 160px;
    }
  }
`;

const greenText = css`
  color: ${theme.colors.green};
`;

const styles = { layout, header, detailButton, table, greenText };

export default styles;
