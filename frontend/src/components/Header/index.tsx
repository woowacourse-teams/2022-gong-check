import { css } from '@emotion/react';
import { useState } from 'react';
import { IoIosArrowBack } from 'react-icons/io';
import { useNavigate } from 'react-router-dom';

import logo from '@/assets/logoTitle.svg';

import styles from './styles';

const hostId = 1;

const Header = () => {
  const [isVisibaleButton, setIsVisibaleButton] = useState(true);
  const navigate = useNavigate();

  const handleClickPreviousButton = () => {
    navigate(-1);
  };

  const handleClickLogo = () => {
    navigate(`/enter/${hostId}/spaces`);
  };

  return (
    <div css={styles.header}>
      {isVisibaleButton && <IoIosArrowBack css={styles.arrowBackIcon} onClick={handleClickPreviousButton} size={30} />}
      <div
        css={css`
          position: absolute;
          left: 50%;
          transform: translate(-50%, 10%);
        `}
        onClick={handleClickLogo}
      >
        <img src={logo} alt="logoTitle" />
      </div>
      <div></div>
    </div>
  );
};

export default Header;
