import { css } from '@emotion/react';

import theme from '@/styles/theme';

const layout = ({ isFullSize }: { isFullSize: boolean }) => css`
  background: ${theme.colors.white};
  border-radius: 8px;
  font-size: 16px;
  box-shadow: 2px 2px 2px 2px ${theme.colors.shadow10};
  ${isFullSize &&
  css`
    margin-top: 5rem;
  `}
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
  width: 5rem;
  height: 2rem;
  margin: 0;
  font-size: 1rem;
  padding: 8px 0;
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
  }

  tbody {
    width: 100%;
    display: block;
    overflow-y: scroll;
    height: ${isFullSize ? '100%' : '12.5em'};

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
    min-width: ${isFullSize ? '20em' : '18.8em'};
    max-width: ${isFullSize ? '20em' : '18.8em'};
    word-wrap: break-word;
    text-align: left;
    &:nth-of-type(3) {
      min-width: ${isFullSize ? '20em' : '15em'};
      max-width: ${isFullSize ? '20em' : '15em'};
    }
  }
`;

const styles = { layout, header, detailButton, table };

export default styles;
