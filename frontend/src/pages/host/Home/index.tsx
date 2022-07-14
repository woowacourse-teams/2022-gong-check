import { useNavigate } from 'react-router-dom';

import Button from '@/components/_common/Button';

const Home: React.FC = () => {
  const navigate = useNavigate();

  const handleClickButton = () => {
    navigate('login');
  };

  return (
    <>
      <span>home</span>
      <Button onClick={handleClickButton}>로그인</Button>
    </>
  );
};

export default Home;
