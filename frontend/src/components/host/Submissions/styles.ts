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

const table = ({ isFullSize }: { isFullSize: boolean }) => css`
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
    min-width: ${isFullSize ? '12em' : '6em'};
    max-width: ${isFullSize ? '12em' : '6em'};
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
