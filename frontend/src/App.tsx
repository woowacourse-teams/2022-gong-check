import routes from './Routes';
import { useRoutes } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

import { isShowModalState, modalComponentState } from '@/recoil/modal';

const App = () => {
  const isShowModal = useRecoilValue(isShowModalState);
  const modalComponent = useRecoilValue(modalComponentState);
  const content = useRoutes(routes);

  return (
    <>
      {content}
      {isShowModal && modalComponent}
    </>
  );
};

export default App;
