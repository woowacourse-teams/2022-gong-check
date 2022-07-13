import { css } from '@emotion/react';
import { Outlet } from 'react-router-dom';

const HostLayout: React.FC = () => {
  return (
    <div
      css={css`
        display: flex;
        flex-direction: column;
        width: 100vw;
        height: 100vh;
        background-color: red;
      `}
    >
      <Outlet />
    </div>
  );
};

export default HostLayout;
