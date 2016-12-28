package cn.edu.bjtu.weibo.serviceImpl;

import java.util.List;

import cn.edu.bjtu.weibo.dao.UserDAO;
import cn.edu.bjtu.weibo.daoImpl.UserDAOImpl;
import cn.edu.bjtu.weibo.model.RelationType;
import cn.edu.bjtu.weibo.service.RelationManageService;

public class RelationManageServiceImpl implements RelationManageService{

	private static final int pageIndex=1;
	private static final int pagePerNumber=1000;
	@Override
	public boolean followAction(String userId, String followedUserId) {
		// TODO Auto-generated method stub
		UserDAO userDAO = new UserDAOImpl();
		List<String> follower = userDAO.getFollowing(userId, pageIndex, pagePerNumber);
		for(String str:follower)
		{
			if(str.equals(followedUserId))
			{
				return true;
			}
		}
		userDAO.insertFollower(followedUserId, userId);
		return userDAO.insertFollowing(userId, followedUserId);
	}

	@Override
	public boolean unfollowAction(String userId, String unfollowedUserId) {
		// TODO Auto-generated method stub
		UserDAO userDAO = new UserDAOImpl();
		List<String> follows = userDAO.getFollowing(userId, pageIndex, pagePerNumber);
		for(String str:follows)
		{
			if(str.equals(unfollowedUserId))
			{
				userDAO.deleteFollower(unfollowedUserId,userId);
				return userDAO.deleteFollowing(userId, unfollowedUserId);
			}
		}
		return true;
	}

	@Override
	public boolean removeFollowerAction(String userId, String removedUserId) {
		// TODO Auto-generated method stub
		UserDAO userDAO = new UserDAOImpl();
		List<String> following = userDAO.getFollowers(userId, pageIndex, pagePerNumber);
		for(String str:following)
		{
			if(str.equals(removedUserId))
			{
				userDAO.deleteFollowing(removedUserId, userId);
				return userDAO.deleteFollower(userId, removedUserId);
			}
		}
		return true;
	}

	@Override
	public RelationType getUserRelationType(String userId, String targetUserId) {
		// TODO Auto-generated method stub
		UserDAO userDAO = new UserDAOImpl();
		List<String> follows = userDAO.getFollowers(userId, pageIndex, pagePerNumber);
		List<String> following =userDAO.getFollowing(userId, pageIndex, pagePerNumber);
		boolean isFollower = false;
		boolean isFollowing = false;
		boolean isFf = false;
		boolean isNo = false;
		for(String str:follows)
		{
			if(str.equals(targetUserId))
			{
				isFollower= true;
			}
		}
		for(String str:following)
		{
			if(str.equals(targetUserId))
			{
				isFollowing = true;
			}
		}
		if(isFollower && isFollowing)
		{
			isFf = true;
		}
		if((!isFollower)&&(!isFollowing))
		{
			isNo = true;
		}
		if(isFf)
		{
			return RelationType.ff;
		}
		if(isNo)
		{
			return RelationType.NO;
		}
		if(isFollower)
		{
			return RelationType.follower;
		}
		if(isFollowing)
		{
			return RelationType.following;
		}
		return null;
	}

}
