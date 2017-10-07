package top.yeonon.controller.backend;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.yeonon.common.Const;
import top.yeonon.common.ServerResponse;
import top.yeonon.pojo.Topic;
import top.yeonon.pojo.User;
import top.yeonon.service.ITopicService;
import top.yeonon.service.IUserService;
import top.yeonon.vo.TopicDetailVo;


import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/manage/topic/")
public class TopicManageController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ITopicService topicService;

    //添加主题，用过提交表单添加
    @RequestMapping(value = "add_topic", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> addTopic(Topic topic, HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登录，请登录管理员账号");
        }
        ServerResponse validResponse = userService.checkRole(user);
        if (!validResponse.isSuccess()) {
            return ServerResponse.createByErrorMessage("无权限，请登录管理员账号");
        }
        return topicService.addTopic(topic);
    }

    //批量删除主题，单个删除同样可以使用
    @RequestMapping("batch_delete_topic")
    @ResponseBody
    public ServerResponse<String> batchDeleteTopic(String topicIds, HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登录，请登录管理员账号");
        }

        ServerResponse validResponse = userService.checkRole(user);
        if (!validResponse.isSuccess()) {
            return ServerResponse.createByErrorMessage("无权限，请登录管理员账号");
        }
        return topicService.batchDelete(topicIds);
    }


    //返回List
    @RequestMapping("list")
    @ResponseBody
    public ServerResponse<PageInfo> list(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                         HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登录，请登录管理员账号");
        }

        ServerResponse validResponse = userService.checkRole(user);
        if (!validResponse.isSuccess()) {
            return ServerResponse.createByErrorMessage("无权限，请登录管理员账号");
        }
        return topicService.getTopicList(pageNum, pageSize);
    }

    //模糊查询
    @RequestMapping("search")
    @ResponseBody
    public ServerResponse<PageInfo> search(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                         String topicName,
                                         HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登录，请登录管理员账号");
        }

        ServerResponse validResponse = userService.checkRole(user);
        if (!validResponse.isSuccess()) {
            return ServerResponse.createByErrorMessage("无权限，请登录管理员账号");
        }
        return topicService.searchTopic(topicName, pageNum, pageSize);

    }


    @RequestMapping("update_topic")
    @ResponseBody
    public ServerResponse update(Topic topic, HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登录，请登录管理员账号");
        }

        ServerResponse validResponse = userService.checkRole(user);
        if (!validResponse.isSuccess()) {
            return ServerResponse.createByErrorMessage("无权限，请登录管理员账号");
        }

        return topicService.updateTopic(topic);
    }

    @RequestMapping("detail")
    @ResponseBody
    public ServerResponse<TopicDetailVo> detail(Integer topicId, HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登录，请登录管理员账号");
        }

        ServerResponse validResponse = userService.checkRole(user);
        if (!validResponse.isSuccess()) {
            return ServerResponse.createByErrorMessage("无权限，请登录管理员账号");
        }
        return topicService.getTopicDetail(topicId);
    }
}
