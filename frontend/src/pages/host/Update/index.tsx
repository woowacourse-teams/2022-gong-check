import useUpdate from './useUpdate';
import { FiEye } from '@react-icons/all-files/fi/FiEye';
import { FiEyeOff } from '@react-icons/all-files/fi/FiEyeOff';
import { RiEmotionLine } from '@react-icons/all-files/ri/RiEmotionLine';
import { RiLockPasswordLine } from '@react-icons/all-files/ri/RiLockPasswordLine';

import Button from '@/components/common/Button';

import styles from './styles';

const Update: React.FC = () => {
  const { password, isShowPassword, onChangePassword, onClickToggleShowPassword, onSubmitChangePassword } = useUpdate();

  return (
    <div css={styles.layout}>
      <form css={styles.content} onSubmit={onSubmitChangePassword}>
        <div css={styles.label}>
          <RiEmotionLine size={32} />
          <label htmlFor="profile-name">사용자에게 보여질 닉네임입니다.</label>
        </div>
        <div css={styles.flex}>
          <div css={styles.inputWrapper}>
            <input id="profile-name" type="text" placeholder="새 닉네임 입력" />
          </div>
          <Button css={styles.button}>변경</Button>
        </div>
      </form>

      <form css={styles.content} onSubmit={onSubmitChangePassword}>
        <div css={styles.label}>
          <RiLockPasswordLine size={32} />
          <label htmlFor="password">공간에 입장하기 위한 입장코드입니다.</label>
        </div>
        <div css={styles.flex}>
          <div css={styles.inputWrapper}>
            <input
              id="password"
              type={isShowPassword ? 'text' : 'password'}
              maxLength={4}
              value={password}
              onChange={onChangePassword}
              autoComplete="off"
              placeholder="새 입장 코드 입력"
            />
            {isShowPassword ? (
              <FiEyeOff size={20} onClick={onClickToggleShowPassword} />
            ) : (
              <FiEye size={20} onClick={onClickToggleShowPassword} />
            )}
          </div>
          <Button css={styles.button}>변경</Button>
        </div>
        <span css={styles.infoText}>기본 입장코드는 "0000"입니다.</span>
      </form>
    </div>
  );
};

export default Update;
