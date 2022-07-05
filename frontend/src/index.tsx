/**  @jsxImportSource @emotion/react */

import ReactDOM from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import App from './App';

import { ThemeProvider } from '@emotion/react';

const theme = {
  colors: {
    primary: 'hotpink',
  },
};

const root = ReactDOM.createRoot(document.getElementById('root') as Element);

root.render(
  <BrowserRouter>
    <ThemeProvider theme={theme}>
      <App />
    </ThemeProvider>
  </BrowserRouter>
);
