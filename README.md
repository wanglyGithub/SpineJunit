

# 前序

以前做动画采用的都是采用序列帧播放的，如果动画播放的效果好，需要制作的序列帧图片比较多，图片的数量多会导致包体急剧增大，对于用户体验来说非常差. 这个时候Spine就发挥作用了，Spine目前广泛的应用在游戏开发或者APP中，做2D开发的。

# 什么是Spine 骨骼

骨骼动画（Skeleton animation又称骨架动画，是一种计算机动画技术，它将三维模型分为两部分：用于控制动作的骨，以及用于绘制模型的蒙皮（Skin），框架

# Spine动画骨骼结构

![image](https://note.youdao.com/yws/public/resource/41a2a610654ea29ddd80a3e8c7d6a73a/xmlnote/1F4420F2448046068E023961B4397749/5537)

# 集成

## 添加libgdx-spine 
下载官方的libgdx-spine运行库,github上将运行库源码拷下来，放到工程中，或者自己打包成jar包。[Github](https://github.com/EsotericSoftware/spine-runtimes) 导入运行库之后就可以开始在项目中播放spine动画了。
![image](https://note.youdao.com/yws/public/resource/41a2a610654ea29ddd80a3e8c7d6a73a/xmlnote/073EC3586C244106AA6CFDCC0A84CB91/5554)



## 使用步骤

1. 首先是读取纹理地图集和骨骼数据，这些骨骼数据也包含动画状态数据.

```java
TextureAtlas playerAtlas = new TextureAtlas(Gdx.files.internal("assets/xxx.atlas"));
SkeletonJson json = new SkeletonJson(playerAtlas);
SkeletonData playerSkeletonData = json.readSkeletonData(Gdx.files.internal("assets/xxx.json"));
AnimationStateData playerAnimationData = new AnimationStateData(playerSkeletonData);
```
2. 然后需要一个spriteBatch和骨骼渲染对象

```java
SpriteBatch batch = new SpriteBatch();
SkeletonRenderer skeletonRenderer = new SkeletonRenderer();
```
3. 用第一步读取的骨骼数据和动画状态数据创建一个骨骼和动画状态对象


```java
Skeleton skeleton = new Skeleton(playerSkeletonData);
AnimationState animationState = new AnimationState(playerAnimationData);
```

4. 每个update环节调用

```java
animationState.update(delta);
animationState.apply(skeleton);
```

5. reader 

```java
batch.begin();
skeletonRenderer.draw(batch, skeleton);
batch.end();
```

# 参考

- Spine 官方例子
https://github.com/EsotericSoftware/spine-superspineboy
- 使用合集
https://github.com/EsotericSoftware/spine-runtimes/tree/master/spine-libgdx