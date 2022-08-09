import usePasswordUpdate from './usePasswordUpdate';
import { FiEyeOff, FiEye } from 'react-icons/fi';
import { RiLockPasswordLine } from 'react-icons/ri';

import Button from '@/components/common/Button';

import styles from './styles';

const PasswordUpdate: React.FC = () => {
  const { password, isShowPassword, onChangePassword, onClickToggleShowPassword, onClickChangeButton } =
    usePasswordUpdate();

  return (
    <div css={styles.layout}>
      <RiLockPasswordLine size={152} />
      <span>사용자가 공간에 입장하기 위한 비밀번호입니다.</span>
      <div css={styles.content}>
        <div css={styles.inputWrapper}>
          <input
            type={isShowPassword ? 'text' : 'password'}
            maxLength={4}
            value={password}
            onChange={onChangePassword}
          />
          {isShowPassword ? (
            <FiEyeOff size={20} onClick={onClickToggleShowPassword} />
          ) : (
            <FiEye size={20} onClick={onClickToggleShowPassword} />
          )}
        </div>
        <Button css={styles.button} onClick={onClickChangeButton}>
          변경
        </Button>
      </div>
      <span css={styles.infoText}>기본 입장코드는 "0000"입니다.</span>
    </div>
  );
};

export default PasswordUpdate;
