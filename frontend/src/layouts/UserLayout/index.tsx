import { css } from '@emotion/react';
import { Suspense, useEffect } from 'react';
import { Outlet, useNavigate } from 'react-router-dom';

const UserLayout: React.FC = () => {
  const navigate = useNavigate();

  useEffect(() => {
    if (!localStorage.getItem('user')) {
      navigate('pwd');
    }
  }, []);

  return (
    <div
      css={css`
        display: flex;
        flex-direction: column;
        width: 400px;
        min-height: 100vh;
        background-color: white;
        position: absolute;
      `}
    >
      <Suspense fallback={<div>로딩 스피너</div>}>
        <Outlet />
      </Suspense>
    </div>
  );
};

export default UserLayout;
