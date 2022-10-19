import useSectionDetailModal from './useSectionDetailModal';
import { BiX } from '@react-icons/all-files/bi/BiX';

import Button from '@/components/common/Button';
import Dimmer from '@/components/common/Dimmer';
import LoadingOverlay from '@/components/common/LoadingOverlay';

import ModalPortal from '@/portals/ModalPortal';

import styles from './styles';

const DEFAULT_NO_IMAGE = 'https://velog.velcdn.com/images/cks3066/post/7f506718-7a3c-4d63-b9ac-f21b6417f3c2/image.png';

interface SectionDetailModalProps {
  target: 'section' | 'task';
  sectionIndex: number;
  taskIndex?: number;
  previousImageUrl: string;
  previousDescription: string;
}

const SectionDetailModal: React.FC<SectionDetailModalProps> = props => {
  const { target, sectionIndex, taskIndex } = props;

  const {
    getSectionInfo,
    getTaskInfo,
    fileInput,
    isDisabledButton,
    onChangeImage,
    onChangeText,
    onClickSaveButton,
    closeModal,
    imageUrl,
    description,
    isImageLoading,
  } = useSectionDetailModal(props);

  return (
    <ModalPortal>
      <Dimmer isAbleClick={false}>
        <div css={styles.container}>
          <BiX size={36} onClick={closeModal} />
          <h1>
            {target === 'section'
              ? getSectionInfo(sectionIndex).name
              : getTaskInfo(sectionIndex, taskIndex as number).name}
          </h1>

          <img
            css={styles.image}
            src={imageUrl === '' ? DEFAULT_NO_IMAGE : imageUrl}
            alt=""
            onClick={() => fileInput.current?.click()}
          />
          <input
            type="file"
            accept="image/gif, image/jpg, image/jpeg, image/png, image/svg, , image/webp"
            ref={fileInput}
            onChange={onChangeImage}
          />
          <div css={styles.description}>
            <textarea
              rows={4}
              value={description}
              placeholder="사용자가 확인할 안내사항을 입력주세요."
              maxLength={128}
              onChange={onChangeText}
            />
            <span>{description?.length || 0}/128</span>
          </div>
          <Button css={styles.saveButton(isDisabledButton)} disabled={isDisabledButton} onClick={onClickSaveButton}>
            {target === 'section' ? '업무 정보 저장' : '작업 정보 저장'}
          </Button>
        </div>
        {isImageLoading && <LoadingOverlay />}
      </Dimmer>
    </ModalPortal>
  );
};

export default SectionDetailModal;
