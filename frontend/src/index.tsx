/**  @jsxImportSource @emotion/react */
import ReactDOM from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import App from './App';
import { Global } from '@emotion/react';
import globalStyle from './styles/global';

const root = ReactDOM.createRoot(document.getElementById('root') as Element);

root.render(
  <BrowserRouter>
    <Global styles={globalStyle} />
    <App />
  </BrowserRouter>
);
