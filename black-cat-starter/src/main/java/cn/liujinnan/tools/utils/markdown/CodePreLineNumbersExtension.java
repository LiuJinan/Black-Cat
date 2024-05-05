package cn.liujinnan.tools.utils.markdown;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.util.data.MutableDataHolder;

/**
 * @author ljn
 * @version 1.0
 * @date 2024-04-23 16:25
 */
public class CodePreLineNumbersExtension implements HtmlRenderer.HtmlRendererExtension {
    @Override
    public void rendererOptions(MutableDataHolder options) {
        // add any configuration settings to options you want to apply to everything, here
    }

    @Override
    public void extend(HtmlRenderer.Builder htmlRendererBuilder, String rendererType) {
        htmlRendererBuilder.attributeProviderFactory(CodePreLineNumbersAttributeProvider.Factory());
    }

    public static CodePreLineNumbersExtension create() {
        return new CodePreLineNumbersExtension();
    }
}
