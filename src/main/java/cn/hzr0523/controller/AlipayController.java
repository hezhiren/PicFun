package cn.hzr0523.controller;

import cn.hzr0523.alipay.config.AlipayConfig;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayFundTransToaccountTransferModel;
import com.alipay.api.request.*;
import com.alipay.api.response.AlipayFundTransOrderQueryResponse;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 电脑网站支付案例
 * hezhi
 * 2018/3/28 17:17
 */

@RequestMapping
@Controller
public class AlipayController {

    @RequestMapping("/index.do")
    public String toIndex() {
        return "index";
    }

    /**
     * 电脑网站支付
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/alipay.do")
    public String alipay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = request.getParameter("WIDout_trade_no");
        //付款金额，必填
        String total_amount = request.getParameter("WIDtotal_amount");
        //订单名称，必填
        String subject = request.getParameter("WIDsubject");
        //商品描述，可空
        String body = request.getParameter("WIDbody");
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "JSON", AlipayConfig.charset,
                AlipayConfig.alipay_public_key, AlipayConfig.sign_type); //获得初始化的AlipayClient
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);//在公共参数中设置回跳和通知地址
        //封装请求参数
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("out_trade_no", out_trade_no);
        paramMap.put("product_code", "FAST_INSTANT_TRADE_PAY");
        paramMap.put("total_amount", total_amount);
        paramMap.put("subject", subject);
        paramMap.put("body", body);
//        paramMap.put("passback_params","merchantBizType%3d3C%26merchantBizNo%3d2016010101111");
        //支付可用渠道
        //paramMap.put("enable_pay_channels","balance,moneyFund,pcredit");
        String json = JSON.toJSONString(paramMap);
        alipayRequest.setBizContent(json);
//        alipayRequest.setBizContent("{" +
//                "    \"out_trade_no\":\"20150320010101007\"," +
//                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
//                "    \"total_amount\":1.00," +
//                "    \"subject\":\"Iphone6 16G\"," +
//                "    \"body\":\"Iphone6 16G\"," +
//                "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," +
//                "    \"enable_pay_channels\":\"balance,moneyFund,pcredit\"," +
//                "    \"extend_params\":{" +
//                "    \"sys_service_provider_id\":\"2088102175122344\"" +
//                "    }" +
//                "  }");//填充业务参数
        String form = "";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=" + AlipayConfig.charset);
        response.getWriter().write(form);//直接将完整的表单html输出到页面
        response.getWriter().flush();
        response.getWriter().close();
        return null;
    }

    /**
     * 查询交易
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/tradequery.do")
    public String queryPay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = request.getParameter("WIDTQout_trade_no");
        //商户订单号，商户网站订单系统中唯一订单号，必填
        String trade_no = request.getParameter("WIDTQtrade_no");
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "JSON", AlipayConfig.charset,
                AlipayConfig.alipay_public_key, AlipayConfig.sign_type); //获得初始化的AlipayClient

        //设置请求参数
        AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();

        //封装请求参数
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("out_trade_no", out_trade_no);
        paramMap.put("trade_no", trade_no);
//        paramMap.put("passback_params","merchantBizType%3d3C%26merchantBizNo%3d2016010101111");
        //支付可用渠道
        //paramMap.put("enable_pay_channels","balance,moneyFund,pcredit");
        String json = JSON.toJSONString(paramMap);
        alipayRequest.setBizContent(json);
        String result = "";
        try {
            result = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 退款
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/refund.do")
    public String refund(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "JSON", AlipayConfig.charset,
                AlipayConfig.alipay_public_key, AlipayConfig.sign_type); //获得初始化的AlipayClient
        //设置请求参数
        AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();

        //商户订单号，商户网站订单系统中唯一订单号
        String out_trade_no = new String(request.getParameter("WIDTRout_trade_no").getBytes("ISO-8859-1"), "UTF-8");
        //支付宝交易号
        String trade_no = new String(request.getParameter("WIDTRtrade_no").getBytes("ISO-8859-1"), "UTF-8");
        //请二选一设置
        //需要退款的金额，该金额不能大于订单金额，必填
        String refund_amount = new String(request.getParameter("WIDTRrefund_amount").getBytes("ISO-8859-1"), "UTF-8");
        //退款的原因说明
        String refund_reason = new String(request.getParameter("WIDTRrefund_reason").getBytes("ISO-8859-1"), "UTF-8");
        //标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传
        String out_request_no = new String(request.getParameter("WIDTRout_request_no").getBytes("ISO-8859-1"), "UTF-8");

        //封装请求参数
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("out_trade_no", out_trade_no);
        paramMap.put("trade_no", trade_no);
        paramMap.put("refund_amount", refund_amount);
        paramMap.put("refund_reason", refund_reason);
        paramMap.put("out_request_no", out_request_no);
        //paramMap.put("passback_params","merchantBizType%3d3C%26merchantBizNo%3d2016010101111");
        //支付可用渠道
        //paramMap.put("enable_pay_channels","balance,moneyFund,pcredit");
        String json = JSON.toJSONString(paramMap);
        alipayRequest.setBizContent(json);
        String result = "";
        try {
            result = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 查询退款
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/queryRefund.do")
    public String queryRefund(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "JSON", AlipayConfig.charset,
                AlipayConfig.alipay_public_key, AlipayConfig.sign_type); //获得初始化的AlipayClient
        //设置请求参数
        AlipayTradeFastpayRefundQueryRequest alipayRequest = new AlipayTradeFastpayRefundQueryRequest();

        //商户订单号，商户网站订单系统中唯一订单号
        String out_trade_no = request.getParameter("WIDRQout_trade_no");
        //支付宝交易号
        String trade_no = request.getParameter("WIDRQtrade_no");
        //请二选一设置
        //请求退款接口时，传入的退款请求号，如果在退款请求时未传入，则该值为创建交易时的外部交易号，必填
        String out_request_no = request.getParameter("WIDRQout_request_no");

        //封装请求参数
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("out_trade_no", out_trade_no);
        paramMap.put("trade_no", trade_no);
        paramMap.put("out_request_no", out_request_no);
        //paramMap.put("passback_params","merchantBizType%3d3C%26merchantBizNo%3d2016010101111");
        //支付可用渠道
        //paramMap.put("enable_pay_channels","balance,moneyFund,pcredit");
        String json = JSON.toJSONString(paramMap);
        alipayRequest.setBizContent(json);
        String result = "";
        try {
            result = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 交易关闭
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/close.do")
    public String close(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "JSON", AlipayConfig.charset,
                AlipayConfig.alipay_public_key, AlipayConfig.sign_type); //获得初始化的AlipayClient
        //设置请求参数
        AlipayTradeCloseRequest alipayRequest = new AlipayTradeCloseRequest();
        //商户订单号，商户网站订单系统中唯一订单号
        String out_trade_no = new String(request.getParameter("WIDTCout_trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //支付宝交易号
        String trade_no = new String(request.getParameter("WIDTCtrade_no").getBytes("ISO-8859-1"),"UTF-8");

        //封装请求参数
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("out_trade_no", out_trade_no);
        paramMap.put("trade_no", trade_no);
        //支付可用渠道
        //paramMap.put("enable_pay_channels","balance,moneyFund,pcredit");
        String json = JSON.toJSONString(paramMap);
        alipayRequest.setBizContent(json);
        String result = "";
        try {
            result = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 转账
     * @param req
     * @param res
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/transfor.do")
    public String transfor(HttpServletRequest req, HttpServletResponse res) throws Exception {
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig
                .merchant_private_key, "JSON", AlipayConfig.charset,
                AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
        AlipayFundTransToaccountTransferModel model = new AlipayFundTransToaccountTransferModel();
        model.setAmount("0.01");
        model.setOutBizNo("3142321423437");
        model.setPayeeType("ALIPAY_LOGONID");
        model.setPayeeAccount("xglukq3944@sandbox.com");
        model.setPayerShowName("上海交通卡退款");
        model.setPayeeRealName("沙箱环境");
        model.setRemark("转账备注");
        request.setBizModel(model);
        AlipayFundTransToaccountTransferResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {
            System.out.println(response.getBody());
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
        return null;
    }

    //查询转账
    @ResponseBody
    @RequestMapping("/queryTransfor.do")
    public String queryTransfor(HttpServletRequest req, HttpServletResponse res) throws Exception {
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig
                .merchant_private_key, "JSON", AlipayConfig.charset,
                AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        AlipayFundTransOrderQueryRequest  request = new AlipayFundTransOrderQueryRequest ();
        // Map<String,Object> paramMap = new HashMap<String, Object>();
        //paramMap.put("out_biz_no", "3142321423435");
        //paramMap.put("order_id","20180329110070001502340000058828");
        request.setBizContent("{" +
                "\"out_biz_no\":\"3142321423437\"," +
                "\"order_id\":\"\"" +
                "  }");
        //request.setBizContent(JSON.toJSONString(paramMap));
        AlipayFundTransOrderQueryResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {
            System.out.println(response.getBody());
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
        return null;
    }




}
