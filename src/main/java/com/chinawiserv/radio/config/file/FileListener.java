package com.chinawiserv.radio.config.file;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import com.chinawiserv.radio.config.NeedAsyn;

@Component
@NeedAsyn(aysn_method = "startWatch")
public class FileListener {
	@Autowired
	ApplicationContext applicationContext;
	private static WatchService watchService;
	private final static Kind<?>[] kinds = new Kind[] { StandardWatchEventKinds.ENTRY_CREATE,
			StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.OVERFLOW };
	private final static Logger logger = LoggerFactory.getLogger(FileListener.class);
	private final static ConcurrentHashMap<WatchKey, Set<Object>> configMap = new ConcurrentHashMap<>();
	private final static ConcurrentHashMap<Object, Method> methodMap = new ConcurrentHashMap<>();
	private final static ConcurrentHashMap<Path, WatchKey> registedPath = new ConcurrentHashMap<>();
	static {

		try {
			watchService = getWatchService();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Async
	public void startWatch(String string_path) throws InterruptedException, IOException {
		File mapperFolder = ResourceUtils.getFile("classpath:".concat(string_path));
		System.out.println(mapperFolder.isDirectory());
		// File mapperFolder2 =
		// applicationContext.getResource("classpath:".concat(string_path)).getFile();
		Path path = Paths.get(mapperFolder.getAbsolutePath());
		path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_CREATE);
		Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				if (dir.toFile().isDirectory()) {
					dir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
							StandardWatchEventKinds.ENTRY_MODIFY);

				}
				return FileVisitResult.CONTINUE;
			}
		});
		while (!Thread.currentThread().isInterrupted()) {
			final WatchKey key = watchService.take();
			System.out.println("监听事件：" + key.watchable().toString());
			final Set<Object> list = configMap.putIfAbsent(key, Collections.emptySet());
			try {
				int count = 1;
				for (Iterator<WatchEvent<?>> ite = key.pollEvents().iterator(); ite.hasNext();) {
					final WatchEvent<?> event = ite.next();
					ite.remove();
					count += event.count();
					if (count < 2) {
						continue;
					}
					Path root = (Path) key.watchable();
					Path aim = Paths.get(root.toAbsolutePath().toString(), event.context().toString());
					if (aim != null && Files.exists(aim) && Files.isReadable(aim)) {
						if (Files.isDirectory(path)) {
							for (Object obj : list) {
								regist(obj, path);
							}
						}
						onFileChange(list, path);
					}
				}
			} finally {
				boolean valid = key.reset();
				if (!valid) {
					break;
				}
			}
		}

	}

	public static void regist(Object obj, Path... paths) throws IOException {
		for (Path path : paths) {
			regist(obj, path, kinds);
		}
	}

	public static void regist(Object obj, Path[] paths, Kind<?>[] kinds) throws IOException {
		System.out.println("regist");
		for (Path path : paths) {
			regist(obj, path, kinds);
		}
	}

	 public static void regist(final Object obj, Path path, final Kind<?>[] kinds) throws IOException {
			final boolean isDirectory = (path != null && Files.isDirectory(path));
			if (isDirectory) {

			    // 先将文件夹下所有的子文件夹都注册
			    Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

				    WatchKey key = registedPath.get(dir);
				    key = key == null ? dir.register(watchService, kinds) : key;
				    configMap.putIfAbsent(key, new HashSet<Object>(1));
				    configMap.get(key).add(obj);
				    return super.preVisitDirectory(dir, attrs);
				}
			    });

			    for (Method method : obj.getClass().getMethods()) {
				if (AnnotationUtils.findAnnotation(method, OnFileChange.class) != null) {
				    methodMap.putIfAbsent(obj, method);
				}
			    }

			} else {
			    logger.warn("[" + path + "] can not register, it's directory " + isDirectory);
			}
		    }
	
//	private void appendListenByPath(Path file) throws IOException {
//		Files.walkFileTree(file, new SimpleFileVisitor<Path>() {
//			@Override
//			public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
//				dir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
//				return FileVisitResult.CONTINUE;
//			}
//		});
//	}

	private static WatchService getWatchService() throws Exception {

		return FileSystems.getDefault().newWatchService();

	}

	private void onFileChange(final Set<Object> list, Path path) throws IOException {
		if (Files.isDirectory(path)) {
			Files.walkFileTree(path, new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					for (Object obj : list) {
						final Method m = methodMap.get(obj);
						if (m != null) {
							try {
								m.invoke(obj, file.toString(), file);
							} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
								logger.error("[" + obj.getClass().getCanonicalName() + "] load [" + file + "] error :",
										e);
							}
						}
					}
					return super.visitFile(file, attrs);
				}
			});
		} else {
			for (Object obj : list) {
				final Method m = methodMap.get(obj);
				if (m != null) {
					try {
						m.invoke(obj, path.toString(), path);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						logger.error("[" + obj.getClass().getCanonicalName() + "] load [" + path + "] error :", e);
					}
				}
			}
		}
	}
}
