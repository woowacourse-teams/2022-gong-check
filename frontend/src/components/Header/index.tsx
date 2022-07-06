/**  @jsxImportSource @emotion/react */
import { useState } from 'react';
import { IoIosArrowBack } from 'react-icons/io';
import { useNavigate } from 'react-router-dom';
import styles from './styles';

const hostId = 1;

const Header = () => {
  const [isVisibaleButton, setIsVisibaleButton] = useState(true);
  const navigate = useNavigate();

  const handleClickPreviousButton = () => {
    navigate(-1);
  };

  const handleClickLogo = () => {
    navigate(`${hostId}/spaces`);
  };

  return (
    <div css={styles.header}>
      {isVisibaleButton && <IoIosArrowBack css={styles.arrowBackIcon} onClick={handleClickPreviousButton} size={30} />}
      <h1 onClick={handleClickLogo}>GongCheck</h1>
      <div></div>
    </div>
  );
};

export default Header;
