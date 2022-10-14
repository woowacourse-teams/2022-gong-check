import App from './App';
import errorMessage from './constants/errorMessage';
import { Global } from '@emotion/react';
import { AxiosError } from 'axios';
import ReactDOM from 'react-dom/client';
import { QueryClient, QueryClientProvider } from 'react-query';
import { BrowserRouter } from 'react-router-dom';
import { RecoilRoot } from 'recoil';

import globalStyle from './styles/global';

const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);
const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      suspense: true,
      retry: false,
      useErrorBoundary: true,
    },
    mutations: {
      useErrorBoundary(error) {
        const err = error as AxiosError<{ errorCode: keyof typeof errorMessage }>;
        const errorCode = err.response?.data.errorCode;

        if (!errorCode) return false;

        return ['A001', 'A002', 'A003', 'E001'].includes(errorCode);
      },
    },
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
