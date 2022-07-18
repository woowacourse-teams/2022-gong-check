import usePassword from './usePassword';

import Button from '@/components/_common/Button';

import homeCover from '@/assets/homeCover.png';

import styles from './styles';

const Password: React.FC = () => {
  const { isActiveSubmit, onSubmit, onChange } = usePassword();

  return (
    <div css={styles.layout}>
      <div>
        <img src={homeCover} alt="" />
      </div>
      <div css={styles.textWrapper}>
        <p>비밀번호를 입력해주세요.</p>
        <p>
          <span>관리자가 공유한 비밀번호입니다.</span>
          <span>비밀번호를 잊으셨나요?</span>
          <span>관리자에게 연락해보세요.</span>
        </p>
      </div>
      <form css={styles.form({ isActiveSubmit })} onSubmit={onSubmit}>
        <input onChange={onChange} placeholder="비밀번호를 입력해주세요." required />
        <Button type="submit">확인</Button>
      </form>
    </div>
  );
};

export default Password;
