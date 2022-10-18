import usePassword from './usePassword';

import Button from '@/components/common/Button';
import Profile from '@/components/common/Profile';

import homeCover from '@/assets/homeCover.png';
import homeCoverWebp from '@/assets/homeCover.webp';

import styles from './styles';

const Password: React.FC = () => {
  const { isActiveSubmit, onSubmit, onChangeInput, hostId } = usePassword();

  return (
    <div css={styles.layout}>
      <div css={styles.homeCoverImage}>
        <img srcSet={`${homeCoverWebp}`} src={homeCover} alt="공책" />
      </div>
      <div css={styles.profile}>
        <Profile hostId={hostId} />
        <p>의 공간입니다.</p>
      </div>
      <div css={styles.textWrapper}>
        <p>비밀번호를 입력해주세요.</p>
        <p>
          <span>입장링크와 함께 공유된 비밀번호입니다.</span>
          <span>비밀번호를 잊으셨나요?</span>
          <span>관리자에게 연락해보세요.</span>
        </p>
      </div>
      <form css={styles.form({ isActiveSubmit })} onSubmit={onSubmit}>
        <input
          type="password"
          name="password"
          autoComplete="off"
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
