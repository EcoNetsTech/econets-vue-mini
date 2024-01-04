package cn.econets.blossom.framework.tenant.core.mq.rabbitmq;

import cn.econets.blossom.framework.tenant.core.context.TenantContextHolder;
import cn.econets.blossom.framework.web.core.WebFrameworkUtils;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;


/**
 * RabbitMQ 消息队列的多租户 {@link ProducerInterceptor} 实现类
 *
 * 1. Producer 发送消息时，将 {@link TenantContextHolder} 租户编号，添加到消息的 Header 中
 * 2. Consumer 消费
 */
public class TenantRabbitMQMessagePostProcessor implements MessagePostProcessor {

    @Override
    public Message postProcessMessage(Message message) throws AmqpException {
        Long tenantId = TenantContextHolder.getTenantId();
        if (tenantId != null) {
            message.getMessageProperties().getHeaders().put(WebFrameworkUtils.HEADER_TENANT_ID, tenantId);
        }
        return message;
    }

}
