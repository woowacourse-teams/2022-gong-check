import { useRef, useState } from 'react';
import { BiX } from 'react-icons/bi';

import Button from '@/components/common/Button';
import Dimmer from '@/components/common/Dimmer';

import useModal from '@/hooks/useModal';

import ModalPortal from '@/portals/ModalPortal';

import styles from './styles';

const DEFAULT_NO_IMAGE = 'https://velog.velcdn.com/images/cks3066/post/7f506718-7a3c-4d63-b9ac-f21b6417f3c2/image.png';

const SectionDetailModal: React.FC = () => {
  const { closeModal } = useModal();

  const fileInput = useRef<HTMLInputElement>(null);

  const [imageUrl, setImageUrl] = useState(DEFAULT_NO_IMAGE);
  const [imageFile, setImageFile] = useState<File>();
  const [detailText, setDetailText] = useState('');
  const [isDisabledButton, setIsDisabledButton] = useState(true);

  const onChangeImage = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (!e.target.files) return;
    const imageFile = e.target.files[0];
    const imageUrl = URL.createObjectURL(imageFile);
    setImageFile(imageFile);
    setImageUrl(imageUrl);

    const isAbleButtonState = !!imageUrl || !!detailText;
    setIsDisabledButton(!isAbleButtonState);
  };

  const onChangeText = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setDetailText(e.target.value);

    const isAbleButtonState = !!imageFile || !!e.target.value;
    setIsDisabledButton(!isAbleButtonState);
  };

  return (
    <ModalPortal>
      <Dimmer mode="full" isAbleClick={false}>
        <div css={styles.container}>
          <BiX size={36} onClick={closeModal} />
          <h1>섹션 이름</h1>
          <img css={styles.image} src={imageUrl} alt="" onClick={() => fileInput.current?.click()} />
          <input type="file" accept="image/*" ref={fileInput} onChange={onChangeImage} />
          <div css={styles.detailText}>
            <textarea
              rows={4}
              placeholder="사용자가 확인할 안내사항을 입력주세요. (120자이내)"
              maxLength={120}
              onChange={onChangeText}
            />
            <span>{detailText.length}/120</span>
          </div>
          <Button css={styles.saveButton(isDisabledButton)} disabled={isDisabledButton}>
            저장
          </Button>
        </div>
      </Dimmer>
    </ModalPortal>
  );
};

export default SectionDetailModal;
