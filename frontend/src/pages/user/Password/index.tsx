import usePassword from './usePassword';

import Button from '@/components/common/Button';

import homeCover from '@/assets/homeCover.png';

import styles from './styles';

const Password: React.FC = () => {
  const { isActiveSubmit, onSubmit, onChangeInput } = usePassword();

  return (
    <div css={styles.layout}>
      <div css={styles.homeCoverImage}>
        <img src={homeCover} alt="공책" />
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
        <input
          type="password"
          name="password"
          onChange={onChangeInput}
          placeholder="비밀번호를 입력해주세요."
          required
        />
        <Button type="submit">확인</Button>
      </form>
    </div>
  );
};

export default Password;
