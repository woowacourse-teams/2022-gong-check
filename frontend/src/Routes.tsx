import { Outlet } from 'react-router-dom';

import JobList from '@/pages/JobList';
import SpaceList from '@/pages/SpaceList';
import TaskList from '@/pages/TaskList';

import Header from '@/components/Header';
import InputModal from '@/components/InputModal';

import useModal from '@/hooks/useModal';

const DefaultLayout = () => {
  const { isShowModal, closeModal } = useModal(!localStorage.getItem('user'));

  return (
    <>
      <Header />
      {isShowModal && (
        <InputModal
          title="비밀번호 입력"
          detail="해당 공간의 관계자만 접근할 수 있습니다."
          placeholder="비밀번호를 입력해주세요."
          buttonText="확인"
          closeModal={closeModal}
        />
      )}
      <Outlet />
    </>
  );
};

const routes = [
  {
    path: '/',
    element: <DefaultLayout />,
    children: [
      {
        path: ':hostId',
        children: [
          {
            path: 'spaces',
            element: <SpaceList />,
          },
          {
            path: 'spaces/:spaceId',
            element: <JobList />,
          },
          {
            path: 'spaces/:spaceId/:jobId',
            element: <TaskList />,
          },
        ],
      },
    ],
  },
];

export default routes;
