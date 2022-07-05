/**  @jsxImportSource @emotion/react */
import { useRoutes } from 'react-router-dom';
import Header from './components/Header';
import routes from './Routes';
import { css } from '@emotion/react';

const App = () => {
  const content = useRoutes(routes);

  return (
    <div
      css={css`
        display: flex;
        flex-direction: column;
        width: 400px;
        height: auto;
        border: 1px solid black;
      `}
    >
      <Header />
      {content}
    </div>
  );
};

export default App;
