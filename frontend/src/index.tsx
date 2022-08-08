import App from './App';
import { Global } from '@emotion/react';
import ReactDOM from 'react-dom/client';
import { QueryClient, QueryClientProvider } from 'react-query';
import { BrowserRouter } from 'react-router-dom';
import { RecoilRoot } from 'recoil';

import globalStyle from './styles/global';

const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);
const queryClient = new QueryClient({
  defaultOptions: {
    queries: { suspense: true, retry: false },
  },
});

root.render(
  <BrowserRouter>
    <Global styles={globalStyle} />
    <QueryClientProvider client={queryClient}>
      <RecoilRoot>
        <App />
      </RecoilRoot>
    </QueryClientProvider>
  </BrowserRouter>
);
