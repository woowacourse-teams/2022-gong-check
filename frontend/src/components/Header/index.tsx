/**  @jsxImportSource @emotion/react */
import { useState } from 'react';
import { IoIosArrowBack } from 'react-icons/io';
import { useLocation, useNavigate } from 'react-router-dom';

import styles from './styles';

const Header = () => {
  const [isVisibaleButton, setIsVisibaleButton] = useState(true);

  const navigate = useNavigate();

  const handlePreviousButtonClick = () => {
    navigate(-1);

    // TO DO:
    // 현재 path 네임 알아오고
    // path 네임별로 뒤로갈 위치 정해주기
  };

  return (
    <div css={styles.header}>
      {isVisibaleButton && <IoIosArrowBack css={styles.arrowBackIcon} onClick={handlePreviousButtonClick} size={30} />}
      <h1>GongCheck</h1>
      <div></div>
    </div>
  );
};

export default Header;
