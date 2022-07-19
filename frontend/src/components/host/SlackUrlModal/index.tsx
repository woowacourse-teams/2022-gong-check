import { Suspense } from 'react';

import Dimmer from '@/components/common/Dimmer';
import SlackUrlBox from '@/components/host/SlackUrlBox';

import slackIcon from '@/assets/slackIcon.svg';

import ModalPortal from '@/ModalPortal';

import styles from './styles';

const JOB_LIST = [
  {
    id: 1,
    name: '청소',
  },
  {
    id: 2,
    name: '마감',
  },
];

const SlackUrlModal = () => {
  return (
    <ModalPortal>
      <Dimmer mode="full">
        <div css={styles.container}>
          <div>
            <img css={styles.icon} src={slackIcon} alt="" />
            <h1 css={styles.title}>Slack 알림 URL 편집</h1>
          </div>
          <span css={styles.detail}>사용하시는 Slack 채널의 URL을 추가해주세요.</span>
          {JOB_LIST.map((job, index) => (
            <Suspense key={index} fallback={<div>슬랙 URL API 로딩 중...</div>}>
              <SlackUrlBox jobName={job.name} jobId={job.id} />
            </Suspense>
          ))}
        </div>
      </Dimmer>
    </ModalPortal>
  );
};

export default SlackUrlModal;
